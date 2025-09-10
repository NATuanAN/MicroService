package product_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import product_service.product.dto.ProductDTO;
import product_service.product.model.ProductModel;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(target = "id", ignore = true)
    ProductDTO toDTO(ProductModel entity);

    ProductModel toEntity(ProductDTO dto);
}
