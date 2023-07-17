package ee.mihkel.webshop.dto.parcelmachines;

import lombok.Data;

import java.util.List;

@Data
public class ParcelMachines {
    List<OmnivaPM> omnivaPMs;
    List<SmartPostPM> smartPostPMs;
}
