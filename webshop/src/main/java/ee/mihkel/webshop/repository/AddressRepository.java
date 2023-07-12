package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
