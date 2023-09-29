package com.example.letai.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shipping_address")
public class ShippingAddressEntity {
    @SequenceGenerator(
            name = "shipping_address_sequence",
            sequenceName = "shipping_address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "shipping_address_sequence"
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

    @Column(name="full_name", nullable = false)
    private String fullName;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="city", nullable = false)
    private String city;
    @Column(name="phone", nullable = false)
    private String phone;

//    @OneToOne(mappedBy = "shippingAddress")
//    private OrderEntity shippingAddress;

}
