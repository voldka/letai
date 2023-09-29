package com.example.letai.dto.converter;

import com.example.letai.dto.UserDTO;
import com.example.letai.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDTO toDto(UserEntity entity) {
        UserDTO result = new UserDTO();
        result.setId(entity.getId());
        result.setFullName(entity.getFullName());
        result.setPassword(entity.getPassword());

        result.setEmail(entity.getEmail());
        result.setAddress(entity.getAddress());

        result.setLocked(entity.getLocked());
        result.setEnabled(entity.getEnabled());
        result.setAppUserRole(entity.getAppUserRole());
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
        return result;
    }
}
