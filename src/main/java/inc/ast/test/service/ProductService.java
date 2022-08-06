package inc.ast.test.service;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ProductService {
    private final ProductRepo productRepo;

    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Transactional
    public void productSave(Product product) {
        productRepo.save(product);
    }

    public String formatDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public void createBet(Bet bet, User user, Product product) {
        bet.setUserId(user);
        bet.setProductId(product);
    }
}