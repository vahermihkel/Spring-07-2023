package ee.mihkel.lemmikloomad.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Owner {
    @Id
    private String name;
    private String personalCode;
    private String telephone;
    @OneToMany
    private List<Pet> pets;
}
