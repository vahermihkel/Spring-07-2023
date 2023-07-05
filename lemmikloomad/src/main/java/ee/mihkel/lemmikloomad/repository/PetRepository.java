package ee.mihkel.lemmikloomad.repository;

import ee.mihkel.lemmikloomad.entity.Owner;
import ee.mihkel.lemmikloomad.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CrudRepository       SortingAndPageableRepository
public interface PetRepository extends JpaRepository<Pet, String> {

    // PRAEGU TEHA EI SAA - MEIL POLE OWNERIT
    Pet findFirstByOwnerOrderByWeightAsc(Owner owner); // Kõikide loomade seast kõige suurema kaaluga

    Pet findFirstByOwnerOrderByWeightDesc(Owner owner); // Kõikide loomade seast kõige väiksema kaaluga

    List<Pet> findAllByOwnerAndWeightBetween(Owner owner, double weight, double weight2); // Kõikide loomade seast vahemik
    List<Pet> findAllByOwnerAndWeightGreaterThan(Owner owner, double weight); // Kõikide loomade seast sellest suurema kaaluga
}
