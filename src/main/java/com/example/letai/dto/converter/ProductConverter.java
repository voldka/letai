package com.example.letai.dto.converter;


import com.example.letai.dto.ProductDTO;
import com.example.letai.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductDTO toDto(ProductEntity entity) {
        ProductDTO result = new ProductDTO();
        result.setId(entity.getId());
        result.setName(entity.getName());
        result.setDescription(entity.getDescription());
        result.setAmount(entity.getAmount());
        result.setDiscount(entity.getDiscount());
        result.setPrice(entity.getPrice());
        result.setImg(entity.getImg());
        return result;
    }

    public ProductEntity toEntity(ProductDTO dto) {
        ProductEntity result = new ProductEntity();
        result.setId(dto.getId());
        result.setName(dto.getName());
        result.setDescription(dto.getDescription());
        result.setAmount(dto.getAmount());
        result.setDiscount(dto.getDiscount());
        result.setPrice(dto.getPrice());
        result.setImg(dto.getImg());
        return result;
    }

    public ProductEntity toEntity(ProductEntity result, ProductDTO dto) {
        result.setId(dto.getId());
        result.setDescription(dto.getDescription());
        result.setAmount(dto.getAmount());
        result.setDiscount(dto.getDiscount());
        result.setName(dto.getName());
        result.setImg(dto.getImg());
        result.setPrice(dto.getPrice());
        return result;
    }
}
