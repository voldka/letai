package com.example.letai.config.authenticate.config.user;

import com.example.letai.model.body.user.UpdateUserBody;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.ProductEntity;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.model.entity.enums.AppUserRole;
import com.example.letai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> roles = null;


        Optional<UserEntity> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            roles = Arrays.asList(new SimpleGrantedAuthority(user.get().getRole().toString()));
            return new User(user.get().getEmail(), user.get().getPassword(), roles);
        }
        throw new UsernameNotFoundException("User not found with the name " + username);
    }

    public UserDTO save(UserDTO user) throws Exception {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Optional<UserEntity> newUser = userRepository.findByEmail(user.getEmail());
        if(newUser.isPresent()){
            throw new Exception();
        }else{
            UserEntity userEntity = userConverter.toEntity(user);
            return userConverter.toDto(userRepository.save(userEntity));
        }
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

