package com.example.letai.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "accesstoken")
public class AccessToken {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
