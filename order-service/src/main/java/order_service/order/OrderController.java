package order_service.order;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import order_service.order.dto.OrderCreate;
import order_service.order.model.OrderModel;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    private Jwt jwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Jwt) authentication.getPrincipal();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderModel> getOrderbyId(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.getOrderbyId(orderId));
    }

    @PostMapping("/createorder")
    public void CreateOrder(@RequestBody OrderCreate orderDto) {
        String userId = jwt().getClaimAsString("sub");// this is user excecute creating the order
        orderService.CreateOrder(orderDto.getItems(), userId);
    }

}
