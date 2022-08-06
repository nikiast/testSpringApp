package inc.ast.test.controller;

import inc.ast.test.model.product.Product;
import inc.ast.test.service.BetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainPageController {
    private final BetService betService;

    public MainPageController(BetService betService) {
        this.betService = betService;
    }

    @GetMapping
    public String index(Model model) {
        Map<Product, Integer> priceProductMap = betService.getProductPriceMap();
        model.addAttribute("priceProductMap", priceProductMap);
        return "main";
    }
}