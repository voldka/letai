package com.example.letai.services;


import com.example.letai.model.dto.ProductDTO;
import com.example.letai.model.dto.converter.ProductConverter;
import com.example.letai.model.entity.ProductEntity;
import com.example.letai.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> listAll() {
        List<ProductDTO> list = new ArrayList<>();
        Iterable<ProductEntity> iterable = productRepository.findAll();
        for (ProductEntity item : iterable) {
            list.add(productConverter.toDto(item));
        }
        return list;
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<ProductEntity> rs = productRepository.findAll(pageable);
        Page<ProductDTO> dtos = rs.map(productEntity -> {
            return productConverter.toDto(productEntity);
        });
        return dtos;
    }

    public ProductDTO save(ProductDTO productDTO) {
        ProductEntity productEntity;
        productEntity = productConverter.toEntity(productDTO);
        ProductEntity rs = productRepository.save(productEntity);
        if (rs != null && rs.getId() != 0) {
            ProductDTO results = productConverter.toDto(productEntity);
            return results;
        } else {
            return null;
        }
    }

    public ProductDTO get(Long id) {
        Optional<ProductEntity> result = productRepository.findById(id);
        if (result.isPresent()) {
            ProductDTO rs = productConverter.toDto(result.get());
            return rs;
        }
        return null;
    }

    public void delete(Long id) {
        Long count = productRepository.countById(id);
        productRepository.deleteById(id);
    }

}
