package ee.mihkel.lemmikloomad.dto;

import ee.mihkel.lemmikloomad.entity.Pet;
import lombok.Data;

import java.util.List;

@Data // tagataustal: @NoArgsConstructor  @Getter   @Setter
public class OwnerDTO {
    private String name;
    private List<Pet> pets;
}
