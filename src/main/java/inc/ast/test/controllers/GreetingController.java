package inc.ast.test.controllers;

import inc.ast.test.entitys.Product;
import inc.ast.test.repos.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
    private ProductRepo productRepo;

    public GreetingController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Product> productList = productRepo.findAll();
        model.addAttribute("products", productList);
        return "index";
    }

    @PostMapping
    public String add(@RequestParam(name="name") String name, @RequestParam(name="price") String price,
                        @RequestParam(name="description") String description, Model model){
        Product product = new Product(name, price, description);
        productRepo.save(product);
        return "redirect:/";
    }
}