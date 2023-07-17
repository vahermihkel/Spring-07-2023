package ee.mihkel.webshop.dto.nordpool;

import java.util.ArrayList;

@lombok.Data
public class Data {
    public ArrayList<TimestampPrice> ee;
    public ArrayList<TimestampPrice> fi;
    public ArrayList<TimestampPrice> lv;
    public ArrayList<TimestampPrice> lt;
}
