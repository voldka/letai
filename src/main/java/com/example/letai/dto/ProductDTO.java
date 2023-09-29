package com.example.letai.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Long price;
    private String description;
    private Integer discount;
    private Long amount;
    private String img;
}
