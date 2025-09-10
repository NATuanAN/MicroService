package product_service.product.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String id;
    @Indexed(unique = true)
    private String productName;
    private Double productPrice;
    private String category;
    private String productDescription;
    private Integer productStock;
    private Boolean isActive;
}
