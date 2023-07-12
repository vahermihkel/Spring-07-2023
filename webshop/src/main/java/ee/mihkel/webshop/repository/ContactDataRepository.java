package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.ContactData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactDataRepository extends JpaRepository<ContactData, Long> {
}
