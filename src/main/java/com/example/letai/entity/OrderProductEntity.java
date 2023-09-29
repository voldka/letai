package com.example.letai.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Data
@Entity
@Table(name = "orders")
public class OrderProductEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    private String paymentMethod;
    private Integer itemsprice;
    private Integer shippingPrice;
    private Integer totalPrice;
    private Boolean isPaid;
    private Date paidAt;
    private Boolean isDelivered;
    private Date deliveredAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // Quan hệ 1-n với đối tượng ở dưới (Person) (1 địa điểm có nhiều người ở)
    // MapopedBy trỏ tới tên biến Address ở trong Person.
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    private Collection<OrderItemEntity> items;

    @Column(name="fullname", nullable = false)
    private String fullName;
    @Column(name="address", nullable = false)
    private String address;
    @Column(name="city", nullable = false)
    private String city;
    @Column(name="phone", nullable = false)
    private String phone;
}
