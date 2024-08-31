package com.sangwon.ecommerce.order.controller;

import com.sangwon.ecommerce.global.auth.userdetails.UserDetailsImpl;
import com.sangwon.ecommerce.order.dto.OrderCreateResponseDto;
import com.sangwon.ecommerce.order.dto.OrderGetStatusResponseDto;
import com.sangwon.ecommerce.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderCreateResponseDto> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        OrderCreateResponseDto orderCreateResponseDto = orderService.createOrder(userId);
        return new ResponseEntity<>(orderCreateResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderGetStatusResponseDto> getOrderStatus(@PathVariable Long orderId) {
        OrderGetStatusResponseDto orderGetStatusResponseDto = orderService.getOrderStatus(orderId);
        return new ResponseEntity<>(orderGetStatusResponseDto, HttpStatus.OK);
    }

    @PatchMapping("/cancel/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/refund/{orderId}")
    public ResponseEntity<Void> refundOrder(@PathVariable Long orderId) {
        orderService.refundOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
