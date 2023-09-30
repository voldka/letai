package com.example.letai.model.body;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int value, String duplicateEmail, String message, LocalDateTime now) {
    }
}
