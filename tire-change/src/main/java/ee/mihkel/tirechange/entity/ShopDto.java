package ee.mihkel.tirechange.entity;

import lombok.Data;

@Data
public class ShopDto {
    private String name; // Manchester või London
    private String address;
    private boolean privateCar;
    private boolean truck;

    // private Map<Vehicle, Boolean>
    // private List<VehicleType>;     VehicleType.PRIVATE_CAR
}
