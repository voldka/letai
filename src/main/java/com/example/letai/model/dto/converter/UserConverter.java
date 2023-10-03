package com.example.letai.model.dto.converter;

import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.model.entity.enums.AppUserRole;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDTO toDto(UserEntity entity) {
        UserDTO result = new UserDTO();

        result.setId(entity.getId());
        result.setEmail(entity.getEmail());

        result.setPassword(entity.getPassword());
        result.setFullName(entity.getFullName());
        result.setAddress(entity.getAddress());

        result.setLocked(entity.getLocked());
        result.setRole(String.valueOf(entity.getRole()));
        result.setPhone(entity.getPhone());

        result.setCity(entity.getCity());
        result.setAvatar(entity.getAvatar());
        result.setEnabled(entity.getEnabled());

        return result;
    }

    public UserEntity toEntity(UserDTO dto) {
        UserEntity result = new UserEntity();

        result.setId(dto.getId());
        result.setAddress(dto.getAddress());
        result.setEmail(dto.getEmail());

        result.setPassword(dto.getPassword());
        result.setFullName(dto.getFullName());
        result.setLocked(dto.getLocked());

        result.setEnabled(dto.getEnabled());
        result.setRole(AppUserRole.valueOf(dto.getRole()));

        result.setCity(dto.getCity());
        result.setPhone(dto.getPhone());
        result.setCity(dto.getCity());
        return result;
    }

    public UserEntity toEntity(UserEntity result, UserDTO dto) {
        result.setId(dto.getId());
        result.setAddress(dto.getAddress());
        result.setEmail(dto.getEmail());

        result.setPassword(dto.getPassword());
        result.setFullName(dto.getFullName());
        result.setLocked(dto.getLocked());

        result.setEnabled(dto.getEnabled());
        result.setRole(AppUserRole.valueOf(dto.getRole()));
        result.setAvatar(dto.getAvatar());
        result.setPhone(dto.getPhone());
        result.setCity(dto.getCity());
        return result;
    }
}
