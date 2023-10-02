package com.example.letai.services;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import com.example.letai.exception.exceptionhandler.EmailAlreadyExistsException;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 4/30/2019
 * Github: https://github.com/loda-kun
 */
@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserDTO save(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity(userConverter.toEntity(userDTO));
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity = userConverter.toEntity(userDTO);
        if (!userRepository.findByEmail(userEntity.getEmail()).isPresent()) {
            UserEntity users = userRepository.save(userEntity);
            if (users != null && users.getId() != 0) {
                return userConverter.toDto(users);
            } else {
                return null;
            }
        } else {
            throw new EmailAlreadyExistsException("email already exist");
        }
    }
}