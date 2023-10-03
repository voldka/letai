package com.example.letai.model.entity;



import com.example.letai.model.entity.enums.AppUserRole;
import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "email" , nullable = false)
    private String email;
    @Column(name = "password",nullable = false)
    private String password;
    @Column(name = "fullname",nullable = false, length = 45)
    private String fullName;
    @Column(name = "address", nullable = true)
    private String address ="chưa cập nhật";
    @Enumerated(EnumType.STRING)
    private AppUserRole role;
    private String phone;
    private String avatar;
    private String city;
    private Boolean locked = false;
    private Boolean enabled = false;

    public UserEntity(Long id, String email, String password, String fullName, AppUserRole appUserRole, String phone, String avatar, String city, String address) {
        this.id = id;
        this.email = email;
        this.password = password;

        this.fullName = fullName;
        this.role = appUserRole;
        this.phone = phone;

        this.avatar = avatar;
        this.city = city;
        this.address = address;
    }

    public UserEntity(UserEntity entity) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




//    @Override
//    public String getUsername() {
//        return userName;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !locked;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }

}
