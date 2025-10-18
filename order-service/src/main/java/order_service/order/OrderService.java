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

    public OrderModel getOrderbyId(String orderId) {
        return orderRepo.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order does not exist"));
    }

    public void CreateOrder(Map<String, Integer> temp, String userId) {
        if (temp == null || userId.isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No temp or userId in the request");
        try {
            Map<String, Integer> resMap = (Map<String, Integer>) rabbitTemplate.convertSendAndReceive("productExchange",
                    "product.key", temp);
            System.out.println("The message is sent");

            OrderModel tempModel = new OrderModel();
            resMap.keySet().forEach(temp::remove);

            tempModel.setItems(temp);
            tempModel.setUserId(userId);
            orderRepo.save(tempModel);

            for (Map.Entry<String, Integer> entry : resMap.entrySet()) {
                System.out.println("There are just " + entry.getValue() + entry.getKey() + "in product database");
            }
        } catch (Exception e) {
            System.out.println("The error in order service" + e);
            throw e;
        }
    }
}
