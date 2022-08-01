package inc.ast.test.service;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final BetRepo betRepo;
    private final ProductRepo productRepo;

    public ProductService(BetRepo betRepo, ProductRepo productRepo) {
        this.betRepo = betRepo;
        this.productRepo = productRepo;
    }

    public Map<Product, Integer> getProductPriceMap() {
        List<Bet> betList = betRepo.findAll();
        Map<Product, Integer> priceProductMap = new HashMap<>();
        Product productFromBetList;
        for (Bet bet : betList) {
            productFromBetList = bet.getProductId();

            if (!priceProductMap.containsKey(productFromBetList)) {
                priceProductMap.put(productFromBetList, bet.getPrice());
            } else {
                Integer currentPrice = priceProductMap.get(productFromBetList);
                if (currentPrice > bet.getPrice()) {
                    priceProductMap.put(bet.getProductId(), bet.getPrice());
                }
            }
        }
        return priceProductMap;
    }

    @Transactional
    public void productSave(Product product) {
        productRepo.save(product);
    }

    @Transactional
    public void betSave(Bet bet) {
        betRepo.save(bet);
    }

    public List<Bet> findByProductId(Product product) {
        return betRepo.findByProductId(product);
    }

    public String formatDateTimeNow() {
        LocalDateTime registrationTime = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
        return registrationTime.format(format);
    }
}
