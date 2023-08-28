package ee.mihkel.tirechange.controller;

import ee.mihkel.tirechange.entity.Time;
import ee.mihkel.tirechange.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @GetMapping("times-date")
    public List<Time> getTimesByDate(
            @RequestParam Date startDate,
            @RequestParam Date endDate) {
        List<Time> times = timeService.getAllTimes();
        // TODO: Filtreerin kuupäeva järgi
        return times;
    }
}
