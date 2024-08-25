package com.sangwon.ecommerce.item.dto;

import com.sangwon.ecommerce.item.entity.Item;
import lombok.Getter;

@Getter
public class ItemCreateResponseDto {
    private String name;
    private String description;
    private Integer stock;
    private Integer price;

    public ItemCreateResponseDto(Item savedItem) {
        this.name = savedItem.getName();
        this.description = savedItem.getDescription();
        this.stock = savedItem.getStock();
        this.price = savedItem.getPrice();
    }
}
