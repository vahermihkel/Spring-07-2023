package ee.mihkel.lemmikloomad.repository;

import ee.mihkel.lemmikloomad.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, String> {
    // Owner    List<Owner>
    Owner findByPersonalCode(String code); // Hibernate
    // kollane --- midagi on valesti

    @Query("SELECT o FROM Owner o WHERE SIZE(o.pets) > :count")
    List<Owner> findAllByPetsGreaterThan(int count);
}
