package ee.mihkel.webshop.service;

import ee.mihkel.webshop.cache.ProductCache;
import ee.mihkel.webshop.dto.everypay.EverypayResponse;
import ee.mihkel.webshop.entity.Order;
import ee.mihkel.webshop.entity.OrderRow;
import ee.mihkel.webshop.entity.Person;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.exception.NotEnoughInStockException;
import ee.mihkel.webshop.repository.OrderRepository;
import ee.mihkel.webshop.repository.PersonRepository;
import ee.mihkel.webshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    ProductRepository productRepository;

    @Mock
    PersonRepository personRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    ProductCache productCache;

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrderToDb() {
        Person person = new Person();
        when(personRepository.findById(any())).thenReturn(Optional.of(person));
        Order order = new Order();
        order.setId(1L);
        when(orderRepository.save(any())).thenReturn(order);

        Long orderId = orderService.saveOrderToDb(100, Collections.emptyList(), 1L);

        assertEquals(1L, orderId);
    }

    @Test
    void saveOrderToDbThrowsException_IfPersonEmpty() {
        assertThrows(NoSuchElementException.class,
                () -> orderService.saveOrderToDb(100, Collections.emptyList(),1L));
    }

    @Test
    void getTotalSum() throws NotEnoughInStockException, ExecutionException {
        Product product = new Product();
        product.setPrice(20.0);
        product.setId(10L);
        product.setStock(2);

        OrderRow orderRow = new OrderRow();
        orderRow.setQuantity(2);
        orderRow.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productCache.getProduct(10L)).thenReturn(product);

        double totalSum = orderService.getTotalSum(Collections.singletonList(orderRow));

        assertEquals(40.0, totalSum, 0.01);
    }

    @Test
    void getTotalSumThrowsNoSuchElementException_ifProductIdNotFound() {
        Product product = new Product();
        product.setId(10L);

        OrderRow orderRow = new OrderRow();
        orderRow.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.getTotalSum(Collections.singletonList(orderRow)));
    }

    @Test
    void getTotalSumReturnsNotEnoughInStockException_ifNotEnoughStock() throws ExecutionException {
        Product product = new Product();
        product.setPrice(20.0);
        product.setId(10L);
        product.setStock(1);

        OrderRow orderRow = new OrderRow();
        orderRow.setQuantity(2);
        orderRow.setProduct(product);

        when(productRepository.findById(10L)).thenReturn(Optional.of(product));
        when(productCache.getProduct(10L)).thenReturn(product);

        assertThrows(NotEnoughInStockException.class, () -> orderService.getTotalSum(Collections.singletonList(orderRow)));

    }

    @Test
    void makePayment() {
        EverypayResponse response = new EverypayResponse();
        response.setPayment_link("LINK_THAT_EVERYPAY_RETURNS");

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(), eq(EverypayResponse.class)))
                .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));

        orderService.makePayment(100, 1L);
    }

    @Test
    void checkPayment() {
    }
}
