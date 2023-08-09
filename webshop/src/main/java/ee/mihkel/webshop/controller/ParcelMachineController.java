package ee.mihkel.webshop.controller;

import ee.mihkel.webshop.dto.parcelmachines.OmnivaPM;
import ee.mihkel.webshop.dto.parcelmachines.ParcelMachines;
import ee.mihkel.webshop.dto.parcelmachines.SmartPostPM;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class ParcelMachineController {
    // https://www.omniva.ee/locations.json

    @GetMapping("parcel-machines/{country}")
    public ParcelMachines getParcelMachines(@PathVariable String country) {
        String finalCountry = country.toUpperCase();

        RestTemplate restTemplate = new RestTemplate(); // @Bean ja Autowire-dan
        ResponseEntity<OmnivaPM[]> omnivaResponse = restTemplate.exchange("https://www.omniva.ee/locations.json",
                HttpMethod.GET, null, OmnivaPM[].class);

        ParcelMachines parcelMachines = new ParcelMachines();

        List<OmnivaPM> omnivaResult = Arrays.stream(omnivaResponse.getBody())
                                                .filter(e-> e.getA0_NAME().equals(finalCountry))
                                                .toList();

//        List<OmnivaPM> result2 = new ArrayList<>();
//        for (OmnivaPM o: response.getBody()) {
//            if (o.getA0_NAME().equals(finalCountry)) {
//                result2.add(o);
//            }
//        }

        parcelMachines.setOmnivaPMs(omnivaResult);

        ResponseEntity<SmartPostPM[]> smartPostResponse = restTemplate.exchange("https://www.smartpost.ee/places.json",
                HttpMethod.GET, null, SmartPostPM[].class);

        if (finalCountry.equals("EE")) {
            parcelMachines.setSmartPostPMs(Arrays.asList(smartPostResponse.getBody()));
        } else {
            parcelMachines.setSmartPostPMs(new ArrayList<>());
        }

        return parcelMachines;
    }
}
