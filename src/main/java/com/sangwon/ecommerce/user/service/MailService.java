package com.sangwon.ecommerce.user.service;

import com.sangwon.ecommerce.global.util.RedisUtil;
import com.sangwon.ecommerce.user.dto.MailVerifyCodeRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;


    // 메일 발송
    public String sendSimpleMessage(String sendEmail) throws MessagingException {
        String number = createNumber();

        MimeMessage message = createMail(sendEmail, number);
        try {
            javaMailSender.send(message);
            log.info("이메일이 성공적으로 발송되었습니다.");

            redisUtil.saveCodeToRedis(sendEmail, number, authCodeExpirationMillis);

        } catch (MailException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("메일 발송 중 오류가 발생했습니다.");
        }
        return number;
    }

    public String createNumber() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 8; i++) { // 인증 코드 8자리
            int index = random.nextInt(3); // 0~2까지 랜덤, 랜덤값으로 switch문 실행

            switch (index) {
                case 0 -> key.append((char) (random.nextInt(26) + 97)); // 소문자
                case 1 -> key.append((char) (random.nextInt(26) + 65)); // 대문자
                case 2 -> key.append(random.nextInt(10)); // 숫자
            }
        }
        return key.toString();
    }

    public MimeMessage createMail(String mail, String number) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, mail);
        message.setSubject("이메일 인증");
        String body = "";
        body += "<h3>요청하신 인증 번호입니다.</h3>";
        body += "<h1>" + number + "</h1>";
        body += "<h3>감사합니다.</h3>";
        message.setText(body, "UTF-8", "html");

        return message;
    }

    // 인증 코드 검증
    public boolean verifyCode(MailVerifyCodeRequestDto mailVerifyCodeRequestDto) {
        String storedCode = redisUtil.getCodeFromRedis(mailVerifyCodeRequestDto.getEmail());
        if (storedCode != null && storedCode.equals(mailVerifyCodeRequestDto.getCode())) {
            redisUtil.deleteCodeFromRedis(mailVerifyCodeRequestDto.getEmail());
            return true;
        }
        return false;
    }
}
