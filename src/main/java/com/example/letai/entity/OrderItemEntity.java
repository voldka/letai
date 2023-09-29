package com.example.letai.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item")
public class OrderItemEntity {
    @SequenceGenerator(
            name = "order_item_sequence",
            sequenceName = "order_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_item_sequence"
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "amount", nullable = false)
    private Long amount;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "price", nullable = false)
    private Long price;
    private Long discount;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderEntity order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;
}
