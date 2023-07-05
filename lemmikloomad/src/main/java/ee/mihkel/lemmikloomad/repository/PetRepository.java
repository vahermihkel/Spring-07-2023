package ee.mihkel.lemmikloomad.repository;

import ee.mihkel.lemmikloomad.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

            // CrudRepository       SortingAndPageableRepository
public interface PetRepository extends JpaRepository<Pet, String> {
}
