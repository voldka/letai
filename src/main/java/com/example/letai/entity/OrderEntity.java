package com.example.letai.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "order")
public class OrderEntity {
    @SequenceGenerator(
            name = "order_sequence",
            sequenceName = "order_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
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

    @OneToMany(mappedBy = "order",targetEntity=OrderItemEntity.class,orphanRemoval = true, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    private List<OrderItemEntity> OrderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private ShippingAddressEntity shippingAddress;
}


