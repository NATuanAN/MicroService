package order_service.order;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/wtf")
@RequiredArgsConstructor
public class OrderTestController {
    private final OrderService orderService;

}