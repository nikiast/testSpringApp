package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.product.TypeOfProduct;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import inc.ast.test.repository.UserRepo;
import inc.ast.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add() {
        return "product/addProduct";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam(name = "name") String name,
                      @RequestParam(name = "price") Integer price,
                      @RequestParam(name = "description") String description,
                      @RequestParam(name = "role") String role) {
        Product product = new Product(name, description);
        switch (role) {
            case "PC" -> product.setTypeOfProducts(TypeOfProduct.PC);
            case "PHONE" -> product.setTypeOfProducts(TypeOfProduct.PHONE);
            case "TABLET" -> product.setTypeOfProducts(TypeOfProduct.TABLET);
        }
        Bet bet = new Bet(user, product, price);
        productService.productSave(product);
        productService.betSave(bet);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String priceForm(@PathVariable("id") Product product,
                            Model model) {
        List<Bet> betList = productService.findByProductId(product);
        model.addAttribute("betList", betList);
        model.addAttribute("product", product);
        return "product/priceForm";
    }

    @PostMapping("newPrice/{id}")
    public String addNewPriceForm(@AuthenticationPrincipal User userFromSession,
                                  @PathVariable("id") Product product,
                                  @RequestParam Integer price) {
        Bet bet = new Bet(userFromSession, product, price);
        productService.betSave(bet);
        return "redirect:/product/{id}";
    }
}