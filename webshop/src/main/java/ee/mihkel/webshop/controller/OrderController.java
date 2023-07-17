package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.everypay.EverypayData;
import ee.mihkel.webshop.dto.everypay.EverypayResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestController
public class OrderController {

    // KODUS: Kõikide võtmine, Lisamine???, Kustutamine, Ühe võtmine, Muutmine???
    //                      korraga tuleb lisada OrderRow

    @GetMapping("payment/{sum}")
    public String pay(@PathVariable double sum) {

        RestTemplate restTemplate = new RestTemplate();
        String url = "https://igw-demo.every-pay.com/api/v4/payments/oneoff";

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "Basic ZTM2ZWI0MGY1ZWM4N2ZhMjo3YjkxYTNiOWUxYjc0NTI0YzJlOWZjMjgyZjhhYzhjZA==");
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        EverypayData body = new EverypayData();
        body.setApi_username("e36eb40f5ec87fa2"); // EveryPay nõuab, et oleks:    api_username
        body.setAccount_name("EUR3D1");  // Lombok teeb:     setApi_username
        body.setAmount(sum);
        body.setOrder_reference("ab12"+ Math.random() * 99999999); // TODO: Järgmine kord
        body.setNonce("adasdsad3121" + ZonedDateTime.now() + Math.random());
        body.setTimestamp(ZonedDateTime.now().toString());
        body.setCustomer_url("https://maksmine.web.app/makse");

        HttpEntity<EverypayData> httpEntity = new HttpEntity<>(body, headers);
        ResponseEntity<EverypayResponse> response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, EverypayResponse.class);
        return response.getBody().getPayment_link();
    }
}
