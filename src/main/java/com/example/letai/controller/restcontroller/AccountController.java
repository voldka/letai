package com.example.letai.controller.restcontroller;

import com.example.letai.authenticate.config.user.CustomUserDetails;
import com.example.letai.authenticate.jwt.JwtTokenProvider;
import com.example.letai.authenticate.payload.LoginRequest;
import com.example.letai.authenticate.payload.LoginResponse;
import com.example.letai.dto.UserDTO;
import com.example.letai.dto.converter.UserConverter;
import com.example.letai.services.UserService;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity<UserDTO> signUpUser(@Valid @RequestBody UserDTO userDTO) {
        //converter to entity
        UserDTO result =userService.save(userDTO);
        if(result != null){
            return ResponseEntity.status(HttpStatus.OK).body(result);
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
