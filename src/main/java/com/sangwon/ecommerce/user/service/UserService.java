package com.sangwon.ecommerce.user.service;

import com.sangwon.ecommerce.global.auth.jwt.JwtUtil;
import com.sangwon.ecommerce.user.dto.UserCreateRequestDto;
import com.sangwon.ecommerce.user.dto.UserUpdatePasswordRequestDto;
import com.sangwon.ecommerce.user.entity.User;
import com.sangwon.ecommerce.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BytesEncryptor aesBytesEncryptor;
    private final JwtUtil jwtUtil;

    @Transactional
    public void createUser(UserCreateRequestDto userCreateRequestDto) {
        Optional<User> checkUsername = userRepository.findByEmail(userCreateRequestDto.getEmail());
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }
        User user = new User(userCreateRequestDto);
        user.encryptSensitiveData(aesBytesEncryptor);
        user.encodePassword(passwordEncoder);

        userRepository.save(user);
    }


    @Transactional
    public void updatePassword(UserUpdatePasswordRequestDto userUpdatePasswordRequestDto, Long userId, HttpServletRequest req, HttpServletResponse res) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        user.updatePassword(userUpdatePasswordRequestDto.getPassword());
        user.encodePassword(passwordEncoder);

        jwtUtil.deleteToken(req, res);
    }
}
