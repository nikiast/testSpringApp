package inc.ast.test.controller;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.product.TypeOfProduct;
import inc.ast.test.model.user.User;
import inc.ast.test.service.BetService;
import inc.ast.test.service.ImageService;
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
    private final BetService betService;
    private final ImageService imageService;

    public ProductController(ProductService productService, BetService betService, ImageService imageService) {
        this.productService = productService;
        this.betService = betService;
        this.imageService = imageService;
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
        imageService.addImage(file, product);
        productService.createBet(bet, user, product);
        productService.productSave(product);
        betService.betSave(bet);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String priceForm(@PathVariable("id") Product product,
                            Model model) {
        List<Bet> betList = betService.findByProductId(product);
        model.addAttribute("betList", betList);
        model.addAttribute("product", product);
        return "product/priceForm";
    }

    @PostMapping("newPrice/{id}")
    public String addNewPriceForm(@AuthenticationPrincipal User userFromSession,
                                  @PathVariable("id") Product product,
                                  @RequestParam Integer price) {
        Bet bet = new Bet(userFromSession, product, price);
        betService.betSave(bet);
        return "redirect:/product/{id}";
    }
}