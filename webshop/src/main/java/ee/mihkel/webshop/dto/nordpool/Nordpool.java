package ee.mihkel.webshop.dto.nordpool;

import java.util.ArrayList;

@lombok.Data
public class Nordpool {
    public boolean success;
    public Data data; // korrektses rakenduses muudaks Data ära, CountryPrices
}
