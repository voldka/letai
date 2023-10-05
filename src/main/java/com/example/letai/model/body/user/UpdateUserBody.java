package com.example.letai.model.body.user;

import lombok.Data;

@Data
public class UpdateUserBody {

    private String fullName;
    private String address;
    private String phone;
    private String avatar;
    private String city;

}
