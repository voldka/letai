package com.example.letai.exception.exceptionhandler;

import com.example.letai.model.body.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public class EmailAlreadyExistsException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    // phan message loi
    public EmailAlreadyExistsException(String message) {
        super(message);
    }

}
