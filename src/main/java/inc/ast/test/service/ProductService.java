package inc.ast.test.service;

import inc.ast.test.model.product.Bet;
import inc.ast.test.model.product.Product;
import inc.ast.test.model.user.User;
import inc.ast.test.repository.BetRepo;
import inc.ast.test.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ProductService {
    private final BetRepo betRepo;
    private final ProductRepo productRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public ProductService(BetRepo betRepo, ProductRepo productRepo) {
        this.betRepo = betRepo;
        this.productRepo = productRepo;
    }

    public Map<Product, Integer> getProductPriceMap() {
        List<Bet> betList = betRepo.findAll();
        Map<Product, Integer> priceProductMap = new HashMap<>();
        Product productFromBetList;
        for (Bet bet : betList) {
            productFromBetList = bet.getProductId();

            if (!priceProductMap.containsKey(productFromBetList)) {
                priceProductMap.put(productFromBetList, bet.getPrice());
            } else {
                Integer currentPrice = priceProductMap.get(productFromBetList);
                if (currentPrice > bet.getPrice()) {
                    priceProductMap.put(bet.getProductId(), bet.getPrice());
                }
            }
        }
        return priceProductMap;
    }

    @Transactional
    public void productSave(Product product) {
        productRepo.save(product);
    }

    @Transactional
    public void betSave(Bet bet) {
        betRepo.save(bet);
    }

    public List<Bet> findByProductId(Product product) {
        return betRepo.findByProductId(product);
    }

    public String formatDateTimeNow() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy"));
    }

    public void createBet(Bet bet, User user, Product product){
        bet.setUserId(user);
        bet.setProductId(product);
    }

    public boolean addImage(MultipartFile file, Product product) {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                product.setFilename(resultFilename);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}