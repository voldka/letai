package com.example.letai.repository;


import com.example.letai.model.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    ProductEntity save(ProductEntity productEntity);

    Long countById(Long id);


}
