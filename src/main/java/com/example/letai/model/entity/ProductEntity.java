package com.example.letai.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "product")
public class ProductEntity {
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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private Collection<OrderItemEntity> items;
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
