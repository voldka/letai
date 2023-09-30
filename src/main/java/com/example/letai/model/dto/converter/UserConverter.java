package com.example.letai.model.dto.converter;

import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.entity.UserEntity;
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
        result.setAppUserRole(entity.getAppUserRole());
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
        result.setAppUserRole(dto.getAppUserRole());

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
        result.setAppUserRole(dto.getAppUserRole());
        result.setAvatar(dto.getAvatar());
        result.setPhone(dto.getPhone());
        result.setCity(dto.getCity());
        return result;
    }
}
