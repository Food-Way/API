package com.foodway.api.service.user.authentication.dto;

import com.foodway.api.model.ETypeUser;

public class UserLoginDto {
    private String email;
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
