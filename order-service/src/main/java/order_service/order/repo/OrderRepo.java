package order_service.order.repo;

//
import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//
import order_service.order.model.OrderModel;

@Repository
public interface OrderRepo extends MongoRepository<OrderModel, String> {
}
