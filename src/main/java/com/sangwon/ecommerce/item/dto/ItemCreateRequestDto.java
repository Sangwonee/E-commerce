package com.sangwon.ecommerce.item.dto;

import lombok.Getter;

@Getter
public class ItemCreateRequestDto {
    private String name;
    private String description;
    private Integer stock;
    private Integer price;

}
