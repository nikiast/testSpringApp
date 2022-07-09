package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

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
        List<Bet> betList = betRepo.findAll();
        model.addAttribute("betList", betList);
        return "main";
    }
}