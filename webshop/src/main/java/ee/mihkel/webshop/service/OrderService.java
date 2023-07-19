package ee.mihkel.webshop.service;

import ee.mihkel.webshop.dto.everypay.EverypayData;
import ee.mihkel.webshop.dto.everypay.EverypayResponse;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OrderRepository orderRepository;

    public Long saveOrderToDb(double totalSum, List<OrderRow> orderRows, Long personId) {
        Person person = personRepository.findById(personId).get();

        Order order = new Order();
        order.setPaymentState("Initial");
        order.setPerson(person);
        order.setOrderRow(orderRows);
        order.setCreationDate(new Date());
        order.setTotalSum(totalSum);
        Order newOrder = orderRepository.save(order);
        // ID saamise eesmärgil
        return newOrder.getId();
    }

    // ctrl + alt + m
    public double getTotalSum(List<OrderRow> orderRows) throws Exception {
        double totalSum = 0;
        for (OrderRow o : orderRows) {
            Product product = productRepository.findById(o.getProduct().getId()).get();
            if (product.getStock() < o.getQuantity()) {
                throw new Exception("Not enough in stock: " + product.getName() + ", id: " + product.getId()); // TODO:Enda exceptioni
            }
            totalSum += o.getQuantity() * product.getPrice();
        }
        return totalSum;
    }

    public String makePayment(double totalSum, Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username("e36eb40f5ec87fa2"); // EveryPay nõuab, et oleks:    api_username
        body.setAccount_name("EUR3D1");  // Lombok teeb:     setApi_username
        body.setAmount(totalSum);
        body.setOrder_reference(id.toString()); // Tellimuse number
        body.setNonce("adasdsad3121" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url("https://maksmine.web.app/makse");

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EverypayResponse.class);
        return response.getBody().getPayment_link();
    }
}
