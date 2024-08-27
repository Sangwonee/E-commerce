package com.sangwon.ecommerce.order.dto;

import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import lombok.Getter;

@Getter
public class OrderItemResponseDto {
    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Integer orderPrice;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.itemId = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getName();
        this.quantity = orderItem.getQuantity();
        this.orderPrice = orderItem.getOrderPrice();
    }
}
