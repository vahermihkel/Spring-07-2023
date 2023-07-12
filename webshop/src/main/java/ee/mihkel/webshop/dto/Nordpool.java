package ee.mihkel.webshop.dto;

import java.util.ArrayList;

@lombok.Data
public class Nordpool {
    public boolean success;
    public Data data; // korrektses rakenduses muudaks Data ära, CountryPrices
}

@lombok.Data
class Data{
    public ArrayList<TimestampPrice> ee;
    public ArrayList<TimestampPrice> fi;
    public ArrayList<TimestampPrice> lv;
    public ArrayList<TimestampPrice> lt;
}

@lombok.Data
class TimestampPrice{
    public int timestamp;
    public double price;
}
