package com.springdoan.demo.repository;

import com.springdoan.demo.entity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<ProductEntity,Long> {
    ProductEntity save(ProductEntity productEntity);

    Long countById(Long id);
}
