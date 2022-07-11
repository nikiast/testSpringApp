package inc.ast.test.controller;

import inc.ast.test.model.product.Product;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import inc.ast.test.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainPageController {
    private ProductRepo productRepo;
    private BetRepo betRepo;
    private ProductService productService;

    public MainPageController(ProductRepo productRepo, BetRepo betRepo, ProductService productService) {
        this.productRepo = productRepo;
        this.betRepo = betRepo;
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model) {
        Map<Product, Integer> priceProductMap = productService.getPriceProductMap();
        model.addAttribute("priceProductMap", priceProductMap);
        return "main";
    }
}