package com.springdoan.demo.entity;


import com.springdoan.demo.entity.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
    @Column(name = "username", nullable = false, length = 45)
    private String username;
    @Column(name = "password",nullable = false, length = 45)
    private String password;
    @Column(name = "fullname",nullable = false, length = 45)
    private String fullName;

    @Column(name = "email" , nullable = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;

    private String phone;
    private String avatar;
    private String city;
    private Boolean locked = false;
    private Boolean enabled = false;


    @Column(name = "address", nullable = true)
    private String address;

    public UserEntity(Optional<UserEntity> user) {
        UserEntity rs = user.get();
        this.id = rs.id;
        this.locked=rs.locked;
        this.address=rs.address;

        this.appUserRole=rs.appUserRole;
        this.enabled=rs.enabled;
        this.email=rs.email;

        this.fullName=rs.fullName;
        this.username=rs.username;
        this.password=rs.password;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
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
