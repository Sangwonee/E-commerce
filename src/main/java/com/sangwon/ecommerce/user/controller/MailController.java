package com.sangwon.ecommerce.user.controller;

import com.sangwon.ecommerce.user.dto.MailDto;
import com.sangwon.ecommerce.user.dto.MailVerifyCodeRequestDto;
import com.sangwon.ecommerce.user.service.MailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/email")
    public ResponseEntity<String> emailCheck(@RequestBody MailDto mailDto) throws MessagingException, UnsupportedEncodingException {
        String authCode = mailService.sendSimpleMessage(mailDto.getEmail());
        return new ResponseEntity<>(authCode, HttpStatus.OK);
    }

    @PostMapping("/code")
    public ResponseEntity<String> verifyCode(@RequestBody MailVerifyCodeRequestDto mailVerifyCodeRequestDto){
        boolean isVerified = mailService.verifyCode(mailVerifyCodeRequestDto);
        if (isVerified) {
            return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 유효하지 않습니다.");
        }
    }
}
