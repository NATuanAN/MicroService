package product_service.product;

import lombok.RequiredArgsConstructor;
import product_service.mapper.ProductMapper;
import product_service.product.dto.ProductDTO;
import org.springframework.stereotype.Service;
import product_service.product.repo.ProductRepo;
import product_service.product.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

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

    @RabbitListener(queues = "product-queue")
    public void receive(String meaString) {
        // meaString.forEach((key, value) -> System.out.println("key: " + key + "value:
        // " + value));
        System.out.println(meaString);
    }

    public ResponseEntity<List<ProductDTO>> listofproduct() {
        List<ProductModel> temp = productRepo.findAll();
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();
        temp.forEach(item -> productDTOs.add(productMapper.toDTO(item)));
        return ResponseEntity.ok(productDTOs);
    }
}