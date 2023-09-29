package com.example.letai.dto;


import com.example.letai.entity.enums.AppUserRole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@Data
public class UserDTO {

    private Long id;
    @NotBlank(message = "Email is mandatory")
    private String email;
    @NotBlank(message = "Password is mandatory")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullName;

    private String address;

    private Boolean locked = false;
    private Boolean enabled = false;

    private AppUserRole appUserRole = AppUserRole.USER;
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singletonList(authority);
    }
    public boolean isAccountNonExpired() {
        return true;
    }
    public boolean isAccountNonLocked() {
        return !locked;
    }
    public boolean isCredentialsNonExpired() {
        return true;
    }
    public boolean isEnabled() {
        return enabled;
    }


}
