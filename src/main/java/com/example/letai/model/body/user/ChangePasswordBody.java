package com.example.letai.model.body.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordBody {
    private String email;
    private String newPassword;
    private String oldPassword;
}
