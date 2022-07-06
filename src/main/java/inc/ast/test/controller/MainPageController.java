package inc.ast.test.controller;

import inc.ast.test.model.product.Product;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.ProductRepo;
import inc.ast.test.repository.UserServiceRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {
    private ProductRepo productRepo;
    private UserServiceRepo userServiceRepo;

    public MainPageController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Product> productList = productRepo.findAll();
        model.addAttribute("products", productList);
        return "main";
    }

    @GetMapping("/ses")
    public String indexx(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        User user = (User) principal;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails)principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        User user = userServiceRepo.findByUsername(username);
        model.addAttribute("user", user);
        return "ses";
    }

}