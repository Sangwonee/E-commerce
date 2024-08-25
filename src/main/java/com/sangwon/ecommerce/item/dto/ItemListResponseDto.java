package com.sangwon.ecommerce.item.dto;

import com.sangwon.ecommerce.item.entity.Item;
import lombok.Getter;

@Getter
public class ItemListResponseDto {
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Integer price;

    public ItemListResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.description = item.getDescription();
        this.stock = item.getStock();
        this.price = item.getPrice();
    }
}
