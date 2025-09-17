package order_service.order.dto;

import java.util.Map;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreate {
    private Map<String, Integer> items;
}
