package order_service.order;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wtf")
public class OrderTestController {
    private OrderService orderService;

    @GetMapping("/hello")
    public String testcreate() {
        return orderService.CreateOrder();
    }
}
