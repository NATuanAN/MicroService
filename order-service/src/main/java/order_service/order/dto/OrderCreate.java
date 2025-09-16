package order_service.order.dto;

import java.util.Map;
import java.util.UUID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate {
    private String userId;
    private Map<UUID, Integer> items;
}
