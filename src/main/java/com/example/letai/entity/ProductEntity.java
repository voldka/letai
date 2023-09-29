package com.springdoan.demo.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tensanpham")
    private String name;
    @Column(name = "gia")
    private Long price;
    @Column(name = "mota")
    private String description ="không có mô tả";
    @Column(name = "mucgiamgia")
    private Integer discount = 0;
    @Column(name = "soluong")
    private Long amount = 0L;
    @Column(name = "img", nullable = true, length = 255)
    private String img;
    private Long countInStock;
    private Integer rating;
    private Long selled;

    @OneToOne(mappedBy = "product")
    private OrderItemEntity orderItem;
    @Transient
    public String getPhotosImagePath() {
        if (img == null || id == null) return null;
        return "src/main/resources/static/admin-Services/" + id + "/" + img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
