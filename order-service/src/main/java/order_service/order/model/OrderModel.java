package order_service.order.model;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class OrderModel {
    @Id
    private String orderId;
    private String userId;

    // @ElementCollection
    // @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name =
    // "order_id"))

    private Map<String, Integer> items;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}