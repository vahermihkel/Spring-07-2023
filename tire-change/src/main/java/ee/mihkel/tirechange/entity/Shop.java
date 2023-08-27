package ee.mihkel.tirechange.entity;

import lombok.Data;

@Data
public class Shop {
    private String name; // Manchester või London
    private boolean privateCar;
    private boolean truck;

    // private Map<Vehicle, Boolean>
    // private List<VehicleType>;     VehicleType.PRIVATE_CAR
}
