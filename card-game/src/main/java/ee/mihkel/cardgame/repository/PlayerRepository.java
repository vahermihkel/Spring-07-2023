package ee.mihkel.cardgame.repository;

import ee.mihkel.cardgame.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, String> {
}
