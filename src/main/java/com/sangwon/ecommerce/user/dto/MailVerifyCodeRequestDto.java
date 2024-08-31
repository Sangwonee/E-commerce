package com.sangwon.ecommerce.user.dto;

import lombok.Getter;

@Getter
public class MailVerifyCodeRequestDto {
    private String email;
    private String code;
}
