package ee.mihkel.lemmikloomad.repository;

import ee.mihkel.lemmikloomad.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, String> {
}
