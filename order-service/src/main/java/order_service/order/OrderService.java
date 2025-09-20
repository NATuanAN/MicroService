package order_service.order;

import java.util.UUID;
import java.util.HashMap;
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
        try {
            if (temp == null || userId.isBlank())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No temp or userId in the request");
            OrderModel tempModel = new OrderModel();
            tempModel.setItems(temp);
            tempModel.setUserId(userId);
            orderRepo.save(tempModel);
            rabbitTemplate.convertAndSend("productExchange", "product.key", temp);
            System.out.println("The message is sent");

        } catch (Exception e) {
            System.out.println("The error in order service" + e);
            throw e;
        }
    }
}
