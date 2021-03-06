package inc.ast.test.controller;

import inc.ast.test.model.product.Product;
import inc.ast.test.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainPageController {
    private final ProductService productService;

    public MainPageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String index(Model model) {
        Map<Product, Integer> priceProductMap = productService.getProductPriceMap();
        model.addAttribute("priceProductMap", priceProductMap);
        return "main";
    }
}