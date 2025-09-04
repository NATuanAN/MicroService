package order_service.order.model;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "orderitem")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID item_id;

    @Column(nullable = false)
    private UUID productId;

    @ManyToOne
    @JoinColumn(name = "orderId", nullable = false)
    @JsonBackReference
    private OrderModel order;
    @Min(value = 1, message = "The number of item must be more than a item")
    private Integer quantity;

    @Column(nullable = false, precision = 15, scale = 2)
    @Min(value = 0, message = "The price of item must be more than 0$")
    private BigDecimal price;

}
