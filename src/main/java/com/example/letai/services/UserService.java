package com.example.letai.services;


import bsh.util.JConsole;
import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.body.user.UpdateUserBody;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.PasswordResetToken;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.PasswordTokenRepository;
import com.example.letai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService  {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    public UserDTO findByEmail(String email){
        UserDTO rs;
        Optional<UserEntity> user= userRepository.findByEmail(email);
        if(user.isPresent()){
            return userConverter.toDto(user.get());
        }else return null;
    }

    public List<UserDTO> listAll(){
        List<UserDTO> list =new ArrayList<>();
        Iterable<UserEntity> iterable = userRepository.findAll();
        for (UserEntity item : iterable) {
            list.add(userConverter.toDto(item));
        }
        return list;
    }

    public UserDTO get(Long id) throws UserNotFoundException {
        Optional<UserEntity> result = userRepository.findById(id);
        if (result.isPresent()) {
            UserDTO user = userConverter.toDto(result.get());
            return user;
        }
        throw new UserNotFoundException("Could not find any users with ID " + id);
    }
    public void delete(Long id) throws UserNotFoundException {
        Long count = userRepository.countById(id);
        if (count == null || count == 0) {
            throw new UserNotFoundException("Could not find any users with ID " + id);
        }
        userRepository.deleteById(id);
    }
    public UserDTO save(UserDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity newUser = new UserEntity();
        newUser = userConverter.toEntity(user);
        return userConverter.toDto(userRepository.save(newUser));
    }

    public UserDTO updateUser(UpdateUserBody updateUserBody, Long userId) {
        Optional<UserEntity> rs = userRepository.findById(userId);
        if (rs.isPresent()) {
            UserEntity user = rs.get();
            user.setFullName(updateUserBody.getFullName());
            user.setPhone(updateUserBody.getPhone());
            user.setCity(updateUserBody.getCity());
            user.setAddress(updateUserBody.getAddress());
            user.setAvatar(updateUserBody.getAvatar());
            return userConverter.toDto(userRepository.save(user));
        } else {
            throw new UsernameNotFoundException("User not found with the id " + userId);
        }
    }

    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void deleteManyUserByids(List<Long> ids) {
        try {
            userRepository.deleteAllById(ids);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<UserDTO> getAllUser() {
        try {
            List<UserDTO> list = new ArrayList<>();
            Iterable<UserEntity> rs = userRepository.findAll();
            for (UserEntity item : rs) {
                list.add(userConverter.toDto(item));
            }
            return list;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    public UserDTO getDetailsUser(Long userId) {
        try {
            Optional<UserEntity> user =userRepository.findById(userId);
            return user.map(userEntity -> userConverter.toDto(userEntity)).orElse(null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
