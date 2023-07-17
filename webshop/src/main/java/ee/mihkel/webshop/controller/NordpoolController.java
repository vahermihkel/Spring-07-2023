package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.nordpool.Nordpool;
import ee.mihkel.webshop.dto.nordpool.TimestampPrice;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NordpoolController {

    @GetMapping("nordpool") // ee, lt, lv, fi
    public List<TimestampPrice> getNordpoolPrices(
            @RequestParam String country
    ) {
        // https://dashboard.elering.ee/api/nps/price?start=2023-05-20T12%3A59%3A59.999Z&end=2023-05-24T20%3A59%3A59.999Z
        // https://dashboard.elering.ee/api/nps/price?start=2023-05-20T12:59:59.999Z&end=2023-05-24T20:59:59.999Z


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Nordpool> response = restTemplate.exchange("https://dashboard.elering.ee/api/nps/price",
                HttpMethod.GET,null, Nordpool.class);

        // KODUS: Tehke ühe riigi kaupa tagastamine

        Nordpool nordpoolResponse = response.getBody();

        if (nordpoolResponse == null) {
            return new ArrayList<>();
        }

        return switch (country) {
            case "ee" -> response.getBody().getData().getEe();
            case "lv" -> response.getBody().getData().getLv();
            case "lt" -> response.getBody().getData().getLt();
            case "fi" -> response.getBody().getData().getFi();
            default -> new ArrayList<>();
        };
    }
}
