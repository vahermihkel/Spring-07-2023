package ee.mihkel.tirechange.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import ee.mihkel.tirechange.configuration.ShopsConfig;
import ee.mihkel.tirechange.entity.Time;
import ee.mihkel.tirechange.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class TimeController {

    @Autowired
    TimeService timeService;

    @GetMapping("times")
    public List<Time> getAllTimes() throws JsonProcessingException {
        return timeService.getAllTimes();
    }

    @GetMapping("times/{shopName}")
    public List<Time> getShopTimes(@PathVariable String shopName) throws JsonProcessingException {
        ShopsConfig.Shop shop = timeService.findShop(shopName);
        if (shop == null) {
            throw new RuntimeException("Shop not found");
        }
        return timeService.fetchTimes(shop);
    }

    @GetMapping("times-date")
    public List<Time> getTimesByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> endDate) throws JsonProcessingException {
        List<Time> times = timeService.getAllTimes();
        if (startDate.isPresent()) {
            times = times.stream().filter(e -> e.getTime().after(startDate.get())).toList();
        }
        if (endDate.isPresent()) {
            times = times.stream().filter(e -> e.getTime().before(endDate.get())).toList();
        }
        return times;
    }

    @PatchMapping("book-time")
    public void bookTime(@RequestParam String id, @RequestParam String shopName, @RequestParam String contactInformation) {
        ShopsConfig.Shop shop = timeService.findShop(shopName);
        if (shop == null) {
            throw new RuntimeException("Shop not found");
        }
        timeService.makeBooking(id, shop, contactInformation);
    }
}
