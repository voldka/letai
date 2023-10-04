package com.example.letai.services;


import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService  {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
    public boolean save(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity = userConverter.toEntity(userDTO);
        if(userEntity.getAddress()==null){
            userEntity.setAddress("chưa cập nhật");
        }
        UserEntity users = userRepository.save(userEntity);
        if(users != null &&users.getId()!=0){
            return true;
        }else{
            return false;
        }
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

//    public UserDTO loadUserByUsernameAndPassword()
}
