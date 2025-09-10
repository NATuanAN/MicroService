package product_service.product;

import lombok.RequiredArgsConstructor;
import product_service.mapper.ProductMapper;
import product_service.product.dto.ProductDTO;
import org.springframework.stereotype.Service;
import product_service.product.repo.ProductRepo;
import product_service.product.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import product_service.product.exception.ProductNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {
    @Autowired
    private final ProductRepo productRepo;
    private final ProductMapper productMapper;

    public void addProduct(ProductModel newProduct) {
        productRepo.insert(newProduct);
    }

    public ProductDTO getProductbyId(String productId) {
        return productMapper.toDTO(productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product " + productId + " is not found")));
    }
}
