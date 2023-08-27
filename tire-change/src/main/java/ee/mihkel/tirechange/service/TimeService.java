package ee.mihkel.tirechange.service;

import ee.mihkel.tirechange.entity.Shop;
import ee.mihkel.tirechange.entity.Time;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    public List<Time> getManchesterTimes() {
        // TODO: Application.properties sisse
        // TODO: Kuupäev muutuvaks
        String url = "http://localhost:9004/api/v2/tire-change-times?from=2006-01-02";
        // TODO: Autowire
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Time[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Time[].class);

        Shop manchester = new Shop();
        manchester.setName("Manchester");
        manchester.setPrivateCar(true);
        manchester.setTruck(true);

        List<Time> times = Arrays.stream(response.getBody())
                .filter(Time::isAvailable)
                .peek(e -> e.setShop(manchester))
                .collect(Collectors.toList());
        return times;
    }


    public List<Time> getLondonTimes() {
        // TODO: Teisest rakendusest päring
//        String url2 = "http://localhost:9003/api/v1/tire-change-times/available?from=2006-01-02&until=2030-01-02\n";
//        ResponseEntity<Time[]> response2 = restTemplate.exchange(url2, HttpMethod.GET, null, Time[].class);
        return new ArrayList<>();
    }


    public List<Time> getAllTimes() {
        List<Time> times = getManchesterTimes();

        times.addAll(getLondonTimes());

        return times;
    }
}
