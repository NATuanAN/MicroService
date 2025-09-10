package product_service.product;

import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import product_service.product.model.ProductModel;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String helloString() {
        return "hello";
    }

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, String>> addProduct(@RequestBody ProductModel productModel) {
        productService.addProduct(productModel);
        HashMap<String, String> res = new HashMap<>();
        res.put("message", "Product hvae been added");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductModel> getProductbyId(@PathVariable String productId) {
        return ResponseEntity.ok(productService.getProductbyId(productId));
    }
}
