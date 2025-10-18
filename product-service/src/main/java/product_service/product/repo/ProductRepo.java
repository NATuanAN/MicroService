package product_service.product.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product_service.product.model.ProductModel;

@Repository
public interface ProductRepo extends JpaRepository<ProductModel, UUID> {

}