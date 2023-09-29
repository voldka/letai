package com.example.letai.repository;


import com.example.letai.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {
    ProductEntity save(ProductEntity productEntity);

    Long countById(Long id);
}
