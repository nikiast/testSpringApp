package inc.ast.test.repository;

import inc.ast.test.model.product.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BetRepo extends JpaRepository<Bet, Long> {
    List<Bet> findByProductId(Long ProductId);
}
