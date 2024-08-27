package com.sangwon.ecommerce.order.dto;

import com.sangwon.ecommerce.order.entity.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderCreateResponseDto {
    private Long orderId;
    private Long userId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemResponseDto> orderItems;
    private Integer totalPrice;

    public OrderCreateResponseDto(Order savedOrder) {
        this.orderId = savedOrder.getId();
        this.userId = savedOrder.getUser().getId();
        this.orderDate = savedOrder.getOrderDate();
        this.status = savedOrder.getStatus().toString();
        this.orderItems = savedOrder.getOrderItems().stream()
                .map(OrderItemResponseDto::new).toList();
        this.totalPrice = calculateTotalPrice(savedOrder);
    }

    private Integer calculateTotalPrice(Order order) {
        return order.getOrderItems().stream()
                .mapToInt(item -> item.getOrderPrice() * item.getQuantity())
                .sum();
    }
}
