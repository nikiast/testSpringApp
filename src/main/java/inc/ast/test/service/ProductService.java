package inc.ast.test.service;

import inc.ast.test.model.product.Product;
import inc.ast.test.model.product.TypeOfProduct;
import inc.ast.test.repository.ProductRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductService {
    private final ProductRepo productRepo;
    private final ImageService imageService;

    public ProductService(ProductRepo productRepo, ImageService imageService) {
        this.productRepo = productRepo;
        this.imageService = imageService;
    }

    @Transactional
    public void productSave(Product product) {
        productRepo.save(product);
    }


    public void selectProductRole(Product product, String role){
        switch (role) {
            case "PC" -> product.setTypeOfProducts(TypeOfProduct.PC);
            case "PHONE" -> product.setTypeOfProducts(TypeOfProduct.PHONE);
            case "TABLET" -> product.setTypeOfProducts(TypeOfProduct.TABLET);
        }
    }

    public void createProduct(Product product, String role, MultipartFile file){
        selectProductRole(product,role);
        imageService.addImage(file, product);
        product.setCreatedTime(UserService.formatDateTimeNow());
    }
}