package com.sangwon.ecommerce.order.dto;

import com.sangwon.ecommerce.order.entity.Order;
import lombok.Getter;

@Getter
public class OrderGetStatusResponseDto {
    private String status;

    public OrderGetStatusResponseDto(Order order) {
        this.status = order.getStatus().toString();
    }
}
