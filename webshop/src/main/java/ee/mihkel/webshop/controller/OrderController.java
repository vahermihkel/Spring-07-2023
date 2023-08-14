package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.everypay.EverypayData;
import ee.mihkel.webshop.dto.everypay.EverypayResponse;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.exception.NotEnoughInStockException;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import ee.mihkel.webshop.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@Log4j2
public class OrderController {

    // KODUS: Kõikide võtmine, Lisamine???, Kustutamine, Ühe võtmine, Muutmine???
    //                      korraga tuleb lisada OrderRow
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @GetMapping("orders")
    public ResponseEntity<List<Order>> getOrders() {
//        return ResponseEntity.ok(orderRepository.findAll());
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("orders")
    public ResponseEntity<String> addOrder(
            @RequestBody List<OrderRow> orderRows
//            @PathVariable Long personId
    ) throws NotEnoughInStockException, ExecutionException {
        // hiljem ---> võtame tokeni küljest isiku
        String personId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        log.info(personId);

        double totalSum = orderService.getTotalSum(orderRows);
        Long id = orderService.saveOrderToDb(totalSum, orderRows, 1L);
        String paymentUrl = orderService.makePayment(totalSum, id);
//        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.findAll());
        return new ResponseEntity<>(paymentUrl, HttpStatus.CREATED);
    }

    @DeleteMapping("orders/{id}")
    public ResponseEntity<List<Order>> deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
        return new ResponseEntity<>(orderRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return new ResponseEntity<>(orderRepository.findById(id).get(), HttpStatus.OK);
    }

    @PutMapping("orders/{id}")
    public List<Order> updateOrder(@PathVariable Long id, @RequestBody Order order) {
        if (orderRepository.existsById(id)) {
            order.setId(orderRepository.findById(id).get().getId());
            orderRepository.save(order);
        }
        return orderRepository.findAll();
    }

    @GetMapping("check-payment/{paymentReference}")
    public ResponseEntity<Boolean> checkPayment(@PathVariable String paymentReference) {
        return new ResponseEntity<>(orderService.checkPayment(paymentReference),HttpStatus.OK);
    }

//    @GetMapping("payment/{sum}")
//    public String pay(@PathVariable double sum) {


//        return response.getBody().getPayment_link();
//    }

    // eraprojekt ---> 300 000
    // riigiprojekt ---> 10 000 eur -> Nortal        Digilugu.ee
    //         3.5miljonit     65   55
}
