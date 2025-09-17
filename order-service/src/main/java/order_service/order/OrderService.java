package order_service.order;

import java.util.UUID;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import lombok.RequiredArgsConstructor;
import order_service.order.model.OrderModel;
import order_service.order.repo.OrderRepo;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final RabbitTemplate rabbitTemplate;

    public OrderModel getOrderbyId(UUID orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order does not exist"));
    }

    public void CreateOrder(Map<String, Integer> temp, String userId) {
        OrderModel tempModel = new OrderModel();
        tempModel.setItems(temp);
        tempModel.setUserId(userId);
        orderRepo.save(tempModel);
    }

}
