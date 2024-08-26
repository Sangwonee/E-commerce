package com.sangwon.ecommerce.order.dto;

import lombok.Getter;

@Getter
public class OrderCreateRequestDto {
    private Long itemId;
    private Integer quantity;
}
