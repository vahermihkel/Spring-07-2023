package ee.mihkel.lemmikloomad;

import org.springframework.data.jpa.repository.JpaRepository;

            // CrudRepository       SortingAndPageableRepository
public interface PetRepository extends JpaRepository<Pet, String> {
}
