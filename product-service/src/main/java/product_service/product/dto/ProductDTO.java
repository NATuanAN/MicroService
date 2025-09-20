package product_service.product.dto;

import java.util.UUID;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @Id
    private UUID product_id;
    private String productName;
    private Double productPrice;
    private String category;
    private String productDescription;
    private Integer productStock;
    private Boolean isActive;
}
