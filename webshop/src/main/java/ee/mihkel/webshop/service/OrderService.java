package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.dto.everypay.EverypayData;
import ee.mihkel.webshop.dto.everypay.EverypayResponse;
import ee.mihkel.webshop.dto.everypay.PaymentCheck;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.exception.NotEnoughInStockException;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    OrderRepository orderRepository;

    @Value("${everypay.url}")
    String url;

    @Value("${everypay.token}")
    String token;

    @Value("${everypay.customer-url}")
    String customerUrl;

    @Value("${everypay.username}")
    String username;

    @Value("${everypay.account-name}")
    String accountName;

    @Autowired
    RestTemplate restTemplate;

    public Long saveOrderToDb(double totalSum, List<OrderRow> orderRows, Long personId) {
        if (personRepository.findById(personId).isEmpty()) {
            throw new NoSuchElementException("Person not found");
        }
//        if (personRepository.findById(personId).isEmpty()) {
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND, "Person Not Found");
//        }
        Person person = personRepository.findById(personId).get();

        Order order = new Order();
        order.setPaymentState("initial"); // settled       failed, voided, cancelled
        order.setPerson(person);
        order.setOrderRow(orderRows);
        order.setCreationDate(new Date());
        order.setTotalSum(totalSum);
        Order newOrder = orderRepository.save(order);
        // ID saamise eesmärgil
        return newOrder.getId();
    }

    @Autowired
    ProductCache productCache;

    // ctrl + alt + m
    public double getTotalSum(List<OrderRow> orderRows) throws NotEnoughInStockException, ExecutionException {
        double totalSum = 0;
        for (OrderRow o : orderRows) {
            if (productRepository.findById(o.getProduct().getId()).isEmpty()) {
                throw new NoSuchElementException("Product not found");
            }
//            Product product = productRepository.findById(o.getProduct().getId()).get();
            Product product = productCache.getProduct(o.getProduct().getId());
            decreaseStock(o, product);
            totalSum += o.getQuantity() * product.getPrice();
        }
        return totalSum;
    }

    private void decreaseStock(OrderRow o, Product product) throws NotEnoughInStockException, ExecutionException {
        if (product.getStock() < o.getQuantity()) {
            throw new NotEnoughInStockException("Not enough in stock: " + product.getName() + ", id: " + product.getId());
        }
        product.setStock( product.getStock() - o.getQuantity() );
        productRepository.save(product);
        productCache.refreshProduct(product.getId(), product);
    }

    public String makePayment(double totalSum, Long id) {
//        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + token);
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username(username); // EveryPay nõuab, et oleks:    api_username
        body.setAccount_name(accountName);  // Lombok teeb:     setApi_username
        body.setAmount(totalSum);
        body.setOrder_reference(id.toString()); // Tellimuse number
        body.setNonce("adasdsad3121" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url(customerUrl);

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url + "/payments/oneoff", HttpMethod.POST, httpEntity, EverypayResponse.class);
        return response.getBody().getPayment_link();
        // summa arvutuse
        // isiku leidmise
        // tellimuse ära salvestamise
        //         - salvestati maksmata kujul
        // everypay'st genereeriti link
        // ---------------------
        // minnakse EveryPay keskkonda
    }

    // ?order_reference=5123124&payment_reference=d0caa640d065713a890bee0f2ad236548381567a2646c7d56582798544df54e6
    // ?order_reference=5123125&payment_reference=b812ee39f51d247f1cc1f7ccd5e3b21e59586bfea3a9ccb9d2463231bedaf99d
    public Boolean checkPayment(String paymentReference) {

        String everyPayUrl = url + "/payments/" + paymentReference + "?api_username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic " + token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<PaymentCheck> response =
                restTemplate.exchange(everyPayUrl, HttpMethod.GET, httpEntity, PaymentCheck.class);

        Order order = orderRepository.findById(Long.parseLong(response.getBody().order_reference)).get();
        order.setPaymentState(response.getBody().payment_state);
        orderRepository.save(order);

        return response.getBody().payment_state.equals("settled");
    }
}
