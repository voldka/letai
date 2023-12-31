package com.example.letai.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
@Table(name = "accesstoken")
public class AccessToken {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name="token",nullable = false)
    private String token;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;
}
