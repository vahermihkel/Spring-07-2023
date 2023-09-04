package ee.mihkel.tirechange.entity;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Time {
    private UUID uuid;
    private Long id;
    private Date time;
    private boolean available;
    private ShopDto shop;
}
