package com.example.letai.config.authenticate.config.user;

import com.example.letai.config.authenticate.jwt.JwtUtil;
import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.body.user.UpdateUserBody;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.dto.converter.UserConverter;
import com.example.letai.model.entity.PasswordResetToken;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.model.response.GenericResponse;
import com.example.letai.repository.PasswordTokenRepository;
import com.example.letai.repository.UserRepository;
import com.example.letai.services.EmailServiceImpl;
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
public class UserService implements UserDetailsService {
    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private EmailServiceImpl emailService;


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

    public UserDTO findByEmail(String email) {
        UserDTO rs;
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            return userConverter.toDto(user.get());
        } else {
            throw new UsernameNotFoundException("could not fin any users with email " + email);
        }
    }

    public List<UserDTO> listAll() {
        List<UserDTO> list = new ArrayList<>();
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
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
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
            Optional<UserEntity> user = userRepository.findById(userId);
            return user.map(userEntity -> userConverter.toDto(userEntity)).orElse(null);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public boolean forgotPassword(String userEmail) {
        try {
            UserDTO user = findByEmail(userEmail);
            //generate token
            UserDetails userdetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtUtil.generateAccessToken(userdetails);
            createPasswordResetTokenForUser(user, token);
            emailService.sendSimpleMessage(userEmail, "token to rest password", token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public void createPasswordResetTokenForUser(UserDTO user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, userConverter.toEntity(user));
        passwordTokenRepository.save(myToken);
    }

    public GenericResponse resetpassword(String email, String newPassword, String token) {
        try {
            PasswordResetToken passwordResetToken = passwordTokenRepository.findByToken(token);
            if (passwordResetToken != null) {
                //change password
                String oldPassword = findByEmail(email).getPassword();
                GenericResponse genericResponse = updatePassword(email, newPassword, oldPassword);
                if (genericResponse.getError().equals("OK")) {
                    passwordTokenRepository.delete(passwordResetToken);
                }
                return genericResponse;
            } else {
                return new GenericResponse("can not find token in database", "ERR");
            }
        } catch (Exception e) {
            System.out.println(e);
            return new GenericResponse("internal systen err at resetPassword", "ERR");
        }

    }

    public GenericResponse updatePassword(String email, String newPassword, String oldPassword) {
        try {
            UserDTO user = findByEmail(email);
            if (user != null) {
                if (oldPassword == user.getPassword()) {
                    user.setPassword(newPassword);
                    save(user);
                    return new GenericResponse("change password complete", "OK");
                } else {
                    return new GenericResponse("pass not match", "ERR");
                }
            } else {
                return new GenericResponse("user not found", "ERR");
            }
        } catch (Exception e) {
            return new GenericResponse("internal server err at changePassword in userService", "ERR");
        }
    }

    public GenericResponse changePassword(String email, String newPassword, String oldPassword) {
        try {
            UserDTO user = findByEmail(email);
            if (user != null) {
                if (bcryptEncoder.matches(oldPassword, user.getPassword())) {
                    user.setPassword(bcryptEncoder.encode(newPassword));
                    save(user);
                    return new GenericResponse("change password complete", "OK");
                } else {
                    return new GenericResponse("pass not match", "ERR");
                }
            } else {
                return new GenericResponse("user not found", "ERR");
            }
        } catch (Exception e) {
            return new GenericResponse("internal server err at changePassword in userService", "ERR");
        }
    }
}

