package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.product.TypeOfProduct;
import inc.ast.test.model.user.User;
import inc.ast.test.service.ProductService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/add")
    public String add(@ModelAttribute("product") Product product) {
        return "product/addProduct";
    }

    @PostMapping("/add")
    public String add(@AuthenticationPrincipal User user,
                      @ModelAttribute("product") Product product,
                      @ModelAttribute("bet") Bet bet,
                      @RequestParam(name = "price") Integer price,
                      @RequestParam(name = "role") String role,
                      @RequestParam("file") MultipartFile file) {
        product.setCreatedTime(productService.formatDateTimeNow());
        switch (role) {
            case "PC" -> product.setTypeOfProducts(TypeOfProduct.PC);
            case "PHONE" -> product.setTypeOfProducts(TypeOfProduct.PHONE);
            case "TABLET" -> product.setTypeOfProducts(TypeOfProduct.TABLET);
        }
        productService.addImage(file, product);
        productService.createBet(bet, user, product);
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