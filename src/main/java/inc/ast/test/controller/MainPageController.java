package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainPageController {
    private ProductRepo productRepo;
    private BetRepo betRepo;

    public MainPageController(ProductRepo productRepo, BetRepo betRepo) {
        this.productRepo = productRepo;
        this.betRepo = betRepo;
    }

    @GetMapping
    public String index(Model model) {
        Map<Product, Integer> priceProductMap = getPriceProductMap();
        model.addAttribute("priceProductMap", priceProductMap);
        return "main";
    }

    protected Map<Product, Integer> getPriceProductMap() {
        List<Bet> betList = betRepo.findAll();
        Map<Product, Integer> priceProductMap = new HashMap<>();
        for (Bet bet : betList) {
            Product productFromBetList = bet.getProductId();

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