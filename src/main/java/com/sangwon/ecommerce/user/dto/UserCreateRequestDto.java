package com.sangwon.ecommerce.user.dto;

import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
}
