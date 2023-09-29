package com.springdoan.demo.services;

import com.springdoan.demo.converter.ProductConverter;
import com.springdoan.demo.dto.ProductDTO;
import com.springdoan.demo.entity.ProductEntity;
import com.springdoan.demo.exception.UserNotFoundException;
import com.springdoan.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ProductDTO> listAll(){
        List<ProductDTO> list =new ArrayList<>();
        Iterable<ProductEntity> iterable = productRepository.findAll();
        for (ProductEntity item : iterable) {
            list.add(productConverter.toDto(item));
        }
        return list;
    }
    public ProductDTO save(ProductDTO productDTO){
        ProductEntity productEntity;
        productEntity = productConverter.toEntity(productDTO);
        ProductEntity rs = productRepository.save(productEntity);
        if(rs != null &&rs.getId()!=0){
            ProductDTO results = productConverter.toDto(productEntity);
            return results;
        }else{
            return null;
        }
    }
    public ProductDTO get(Long id) throws UserNotFoundException {
        Optional<ProductEntity> result = productRepository.findById(id);
        if (result.isPresent()) {
            ProductDTO rs = productConverter.toDto(result.get());
            return rs;
        }
        throw new UserNotFoundException("Could not find any product with ID " + id);
    }
    public void delete(Long id) throws UserNotFoundException {
        Long count = productRepository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any product with ID " + id);
        }
        productRepository.deleteById(id);
    }

}
