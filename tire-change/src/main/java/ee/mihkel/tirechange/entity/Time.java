package ee.mihkel.tirechange.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Time {
    private String id;
    private Date time;
    private boolean available;
    private ShopDto shop;
}
