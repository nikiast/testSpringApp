package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import inc.ast.test.repository.UserServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
        Iterable<Product> productList = productRepo.findAll();
        List<Bet> betList = betRepo.findAll();
        model.addAttribute("products", productList);
        model.addAttribute("bets", betList);
        return "main";
    }

    @GetMapping("/isAuthorized")
    public void isAuthorized(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof User){
            User user = (User) principal;
            String username = user.getUsername();
            model.addAttribute("userForHeader", username);
        }else {
            model.addAttribute("userForHeader", "unknown");
        }
    }
}