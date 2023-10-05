package com.example.letai.controller.restcontroller;


import com.example.letai.config.authenticate.config.user.CustomUserDetailsService;
import com.example.letai.config.authenticate.jwt.JwtUtil;
import com.example.letai.exception.exceptionhandler.UserNotFoundException;
import com.example.letai.model.body.user.UpdateUserBody;
import com.example.letai.model.dto.UserDTO;
import com.example.letai.model.body.payload.AuthenticationRequest;
import com.example.letai.model.body.payload.AuthenticationResponse;
import com.example.letai.model.entity.RefreshToken;
import com.example.letai.model.entity.UserEntity;
import com.example.letai.model.response.GenericResponse;
import com.example.letai.repository.RefreshTokenRepository;
import com.example.letai.services.EmailServiceImpl;
import com.example.letai.services.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtUtil jwtUtil;


    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String accessToken = jwtUtil.generateAccessToken(userdetails);
        String refreshToken = jwtUtil.generateRefreshToken(userdetails);

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        refreshTokenRepository.save(token);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return ResponseEntity.ok(new AuthenticationResponse(accessToken));
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDTO user) throws Exception {

        return ResponseEntity.ok(userDetailsService.save(user));
    }

    @PutMapping(value = "/update-user/{userId}")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UpdateUserBody updateUserBody, @PathVariable String userId) throws Exception {
        try {
            if (userId == null) {
                throw new IllegalArgumentException("userId parameter is missing");
            }
            Long id = Long.valueOf(userId);
            UserDTO user = userDetailsService.updateUser(updateUserBody,id);

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "User updated successfully");
            response.put("user",user);

            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "Invalid userId parameter");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }


}
