package com.sangwon.ecommerce.user.controller;

import com.sangwon.ecommerce.global.auth.userdetails.UserDetailsImpl;
import com.sangwon.ecommerce.user.dto.UserCreateRequestDto;
import com.sangwon.ecommerce.user.dto.UserUpdatePasswordRequestDto;
import com.sangwon.ecommerce.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
        userService.createUser(userCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdatePasswordRequestDto userUpdatePasswordRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails,
                                           HttpServletRequest req,
                                           HttpServletResponse res) {
        Long userId = userDetails.getUser().getId();
        userService.updatePassword(userUpdatePasswordRequestDto, userId, req, res);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
