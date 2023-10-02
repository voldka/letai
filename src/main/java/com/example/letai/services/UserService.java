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
import com.example.letai.model.body.ErrorResponse;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.UserRepository;
import com.example.letai.authenticate.config.user.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 4/30/2019
 * Github: https://github.com/loda-kun
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Kiểm tra xem user có tồn tại trong database không?
        Optional<UserEntity> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.get());
    }

    // JWTAuthenticationFilter sẽ sử dụng hàm này
    @Transactional
    public UserDetails loadUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return new CustomUserDetails(user);
    }

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