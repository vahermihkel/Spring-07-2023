package ee.mihkel.tirechange.controller;

import ee.mihkel.tirechange.entity.Shop;
import ee.mihkel.tirechange.entity.Time;
import ee.mihkel.tirechange.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class TimeController {

    @Autowired
    TimeService timeService;

    @GetMapping("times")
    public List<Time> getAllTimes() {
        return timeService.getAllTimes();
    }

    @GetMapping("times/{shopName}")
    public List<Time> getShopTimes(@PathVariable String shopName) {
        return switch (shopName) {
            case "Manchester" -> timeService.getManchesterTimes();
            case "London" -> timeService.getLondonTimes();
            default -> new ArrayList<>();
        };
    }

    @GetMapping("times")
    public List<Time> getTimesByDate(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<Time> times = timeService.getAllTimes();
        // TODO: Filtreerin kuupäeva järgi
        return times;
    }
}
