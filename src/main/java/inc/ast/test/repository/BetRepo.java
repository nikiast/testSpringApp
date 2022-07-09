package inc.ast.test.repository;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BetRepo extends JpaRepository<Bet, Long> {
    List<Bet> findByProductId(Product productId);
}
