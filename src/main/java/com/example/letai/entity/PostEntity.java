package com.example.letai.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idposts", nullable = false)
    private Long id;

    @Column(name = "img", nullable = true, length = 255)
    private String img;

    @Transient
    public String getPhotosImagePath() {
        if (img == null || id == null) return null;
        return "src/main/resources/static/admin-posts/" + id + "/" + img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
