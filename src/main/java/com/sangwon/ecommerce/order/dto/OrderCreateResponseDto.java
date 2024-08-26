package com.sangwon.ecommerce.order.dto;

import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResponseDto {
    private Long orderId;
    private Long itemId;
    private Integer quantity;
    private Integer totalPrice;
    private String status;
    private LocalDateTime orderDate;

    public OrderCreateResponseDto(OrderItem saveedOrderItem) {
        this.orderId = saveedOrderItem.getOrder().getId();
        this.itemId = saveedOrderItem.getItem().getId();
        this.quantity = saveedOrderItem.getQuantity();
        this.totalPrice = saveedOrderItem.getTotalPrice();
        this.status = saveedOrderItem.getOrder().getStatus().toString();
        this.orderDate = saveedOrderItem.getOrder().getOrderDate();
    }
}
