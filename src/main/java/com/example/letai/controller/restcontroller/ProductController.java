package com.example.letai.controller.restcontroller;

import com.example.letai.model.dto.ProductDTO;
import com.example.letai.model.entity.ProductEntity;
import com.example.letai.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@RestController
@RequestMapping(value = "/api")
public class ProductController {
    @Autowired
    private ProductService productService;
//    http://localhost:8080/api/listProductPageable?page=0&size=3&sort=name
    @RequestMapping(value = "/listProductPageable", method = RequestMethod.GET)

    Page<ProductDTO> productPageable(Pageable pageable) {
        //Pageable là một interface được sử dụng để đại diện cho thông tin về phân trang (paging) trong các truy vấn dữ liệu.
//        bao gồm số trang, kích thước trang (số lượng phần tử trên mỗi trang), và các tiêu chí sắp xếp (sorting)
        return productService.findAll(pageable);

    }
}
