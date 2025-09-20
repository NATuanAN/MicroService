package product_service.product;

import lombok.RequiredArgsConstructor;
import product_service.mapper.ProductMapper;
import product_service.product.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import product_service.product.repo.ProductRepo;
import product_service.product.model.ProductModel;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
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
        productRepo.save(newProduct);
    }

    public ProductDTO getProductbyId(UUID productId) {
        return productMapper.toDTO(productRepo.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product " + productId + " is not found")));
    }

    @RabbitListener(queues = "product-queue")
    @Transactional
    public void receive(Map<String, Integer> meaString) {
        List<UUID> idofProducts = meaString.keySet().stream().map(UUID::fromString).toList();
        List<ProductModel> listofproduct = productRepo.findAllById(idofProducts);

        for (ProductModel pro : listofproduct) {
            String tempId = pro.getProduct_id().toString();
            int newValue = pro.getProductStock() - meaString.get(tempId);
            if (newValue <= 0)
                pro.setProductStock(0);
            else {
                pro.setProductStock(newValue);
            }
        }
        productRepo.saveAll(listofproduct);
    }

    public ResponseEntity<List<ProductDTO>> listofproduct() {
        List<ProductModel> temp = productRepo.findAll();
        ArrayList<ProductDTO> productDTOs = new ArrayList<>();
        temp.stream().forEach(item -> productDTOs.add(productMapper.toDTO(item)));
        return ResponseEntity.ok(productDTOs);
    }
}