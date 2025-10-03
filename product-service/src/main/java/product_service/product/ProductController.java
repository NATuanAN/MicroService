package product_service.product;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import product_service.product.dto.ProductDTO;
import product_service.product.model.ProductModel;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<HashMap<String, String>> addProduct(@RequestBody ProductModel productModel) {
        productService.addProduct(productModel);
        HashMap<String, String> res = new HashMap<>();
        res.put("message", "Product have been added");
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductbyId(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductbyId(productId));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ProductDTO>> listofproduct() {
        return productService.listofproduct();
    }

    @GetMapping("/test_user")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok(productService.user_queString());
    }
}
