package ee.mihkel.cardgame.repository;

import ee.mihkel.cardgame.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
