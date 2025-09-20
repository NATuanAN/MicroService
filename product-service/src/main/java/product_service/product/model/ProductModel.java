package product_service.product.model;

import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@Builder
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID product_id;
    @NotBlank
    private String productName;
    @NotBlank
    private Double productPrice;
    @NotBlank
    private String category;
    @NotBlank
    private String productDescription;
    @Min(0)
    private Integer productStock;
    @NotBlank
    private Boolean isActive;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    protected void createAt() {
        this.created_at = LocalDateTime.now();
    }

    @PreUpdate
    protected void updated_at() {
        this.created_at = LocalDateTime.now();
    }
}
