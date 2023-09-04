package ee.mihkel.tirechange.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ee.mihkel.tirechange.configuration.ShopsConfig;
import ee.mihkel.tirechange.entity.*;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeService {

    @Autowired
    ShopsConfig shopsConfig;


    public List<Time> getAllTimes() throws JsonProcessingException {
        List<Time> times = new ArrayList<>();

        for (ShopsConfig.Shop shop: getShops()) {
            System.out.println(shop.getName());
            times.addAll(fetchTimes(shop));
        }

        return times;
    }

    public List<Time> fetchTimes(ShopsConfig.Shop shop) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity httpEntity = new HttpEntity(null);
        if (shop.getApplicationType().equals("xml")) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
            httpEntity = new HttpEntity(headers);

            restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        }

        ResponseEntity<String> response = restTemplate.exchange(shop.getTimesUrl(),HttpMethod.GET, httpEntity, String.class);

        List<Time> times = new ArrayList<>();

        ShopDto shopDto = new ShopDto();
        shopDto.setName(shop.getName());
        shopDto.setAddress(shop.getAddress());
        shopDto.setPrivateCar(shop.isPrivateCar());
        shopDto.setTruck(shop.isTruck());

        if (shop.getApplicationType().equals("xml")) {
            TireChangeTimesResponseXml tireChangeTimesResponseXml = unmarshalXml(response.getBody());

            times = tireChangeTimesResponseXml.getAvailableTimes().stream()
                    .peek(e -> e.setShop(shopDto))
                    .peek(e -> e.setAvailable(true))
                    .toList();
        } else if (shop.getApplicationType().equals("json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            Time[] timeArray = objectMapper.readValue(response.getBody(), Time[].class);

            times = Arrays.stream(timeArray)
                    .filter(Time::isAvailable)
                    .peek(e -> e.setShop(shopDto))
                    .toList();
        } else {
            throw new RuntimeException("Application type not handled");
        }

        return times;
    }

    private TireChangeTimesResponseXml unmarshalXml(String xmlData) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TireChangeTimesResponseXml.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlData);
            TireChangeTimesResponseXml tireChangeTimesResponseXml = (TireChangeTimesResponseXml) unmarshaller.unmarshal(reader);
            return tireChangeTimesResponseXml;
        } catch (JAXBException e) {
            throw new RuntimeException("Error unmarshalling XML: " + e.getMessage(), e);
        }
    }

    private List<ShopsConfig.Shop> getShops() {
        return shopsConfig.getShops();
    }


    public ShopsConfig.Shop findShop(String shopName) {
        for (ShopsConfig.Shop shop: getShops()) {
            if (shop.getName().equalsIgnoreCase(shopName)) {
                return shop;
            }
        }
        return null;
    }

    public void makeBooking(String id, ShopsConfig.Shop shop, String contactInformation) {
        RestTemplate restTemplate = new RestTemplate();

        HttpMethod httpMethod;
        switch (shop.getBookMethod()) {
            case "POST" -> httpMethod = HttpMethod.POST;
            case "PUT" -> httpMethod = HttpMethod.PUT;
            default -> throw new RuntimeException("Wrong http method type");
        }

        HttpEntity httpEntity;
        if (shop.getApplicationType().equals("xml")) {
            TireChangeBookingRequestXml request = new TireChangeBookingRequestXml();
            request.setContactInformation(contactInformation);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);
            httpEntity = new HttpEntity(request);
        } else if (shop.getApplicationType().equals("json")) {
            Contact contact = new Contact();
            contact.setContactInformation(contactInformation);
            httpEntity = new HttpEntity(contact);
        } else {
            throw new RuntimeException("Application type not handled");
        }

        ResponseEntity<String> response = restTemplate.exchange(
                shop.getBookingUrl() + id + "/booking",
                httpMethod,
                httpEntity,
                String.class
        );
        System.out.println(response.getBody());
    }
}
