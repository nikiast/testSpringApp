package inc.ast.test.service;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.repository.BetRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private BetRepo betRepo;

    public ProductService(BetRepo betRepo) {
        this.betRepo = betRepo;
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
}
