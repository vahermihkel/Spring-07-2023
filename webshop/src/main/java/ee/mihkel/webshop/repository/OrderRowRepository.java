package ee.mihkel.webshop.repository;

import ee.mihkel.webshop.entity.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRowRepository extends JpaRepository<OrderRow, Long> {
}
