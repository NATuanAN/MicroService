package product_service.product.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import product_service.product.model.ProductModel;

@Repository
public interface ProductRepo extends MongoRepository<ProductModel, String> {

}
