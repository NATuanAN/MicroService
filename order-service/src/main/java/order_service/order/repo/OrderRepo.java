package order_service.order.repo;

//

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//
import order_service.order.model.OrderModel;

@Repository
public interface OrderRepo extends JpaRepository<OrderModel, UUID> {
}
