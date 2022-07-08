package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.product.TypeOfProduct;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import inc.ast.test.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    ProductRepo productRepo;
    BetRepo betRepo;

    public ProductController(ProductRepo productRepo, BetRepo betRepo) {
        this.productRepo = productRepo;
        this.betRepo = betRepo;
    }

    @GetMapping("/add")
    public String add(){
        return "product/addProduct";
    }

    @PostMapping("/add")
    public String add(@RequestParam(name="name") String name,
                      @RequestParam(name="price") String price,
                      @RequestParam(name="description") String description,
                      @RequestParam(name="role") String role){
        Product product = new Product(name, description);
        switch (role) {
            case "PC" -> product.setTypeOfProducts(TypeOfProduct.PC);
            case "PHONE" -> product.setTypeOfProducts(TypeOfProduct.PHONE);
            case "TABLET" -> product.setTypeOfProducts(TypeOfProduct.TABLET);
        }
        User user = UserController.getUserFromSession();
        if(user != null){
            Bet bet = new Bet(user, product, price);
            productRepo.save(product);
            betRepo.save(bet);
        }else {

        }
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String priceForm(@PathVariable("id") Product product,
                            Model model){
        List<Bet> betList = Stream.of(betRepo.findByProductId(product)).toList();
        model.addAttribute("betList", betList);
        model.addAttribute("product", product);
        return "product/priceForm";
    }

    @PostMapping("newPrice/{id}")
    public String addNewPriceForm(@PathVariable("id") Product product,
                                  @RequestParam Integer price){
        User user = UserController.getUserFromSession();
        if(user != null){
            Bet bet = new Bet(user, product, price.toString());
            betRepo.save(bet);
        }
            return "redirect:/product/{id}";
    }
}