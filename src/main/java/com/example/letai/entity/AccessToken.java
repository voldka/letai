package com.example.letai.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "access_token")
public class AccessToken {
    @SequenceGenerator(
            name = "access_token_sequence",
            sequenceName = "access_token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_sequence"
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
