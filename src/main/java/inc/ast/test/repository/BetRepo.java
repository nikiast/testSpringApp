package inc.ast.test.repository;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BetRepo extends JpaRepository<Bet, Long> {
    List<Bet> findByProductId(Product productId);
}