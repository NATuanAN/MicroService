package product_service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import product_service.product.model.ProductModel;
import product_service.product.repo.ProductRepo;
import product_service.product.exception.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepo productRepo;

    public void addProduct(ProductModel newProduct) {
        productRepo.insert(newProduct);
    }

    public ProductModel getProductbyId(String productId) {
        return productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product " + productId + " is not found"));
    }
}
