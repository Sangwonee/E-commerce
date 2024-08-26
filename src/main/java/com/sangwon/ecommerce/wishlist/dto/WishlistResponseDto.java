package com.sangwon.ecommerce.wishlist.dto;

import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import lombok.Getter;

@Getter
public class WishlistResponseDto {
    private Long itemId;
    private String name;
    private Integer price;
    private Integer stick;

    public WishlistResponseDto(ItemWishlist itemWishlist) {
        this.itemId = itemWishlist.getItem().getId();
        this.name = itemWishlist.getItem().getName();
        this.price = itemWishlist.getItem().getPrice();
        this.stick = itemWishlist.getItem().getStock();
    }
}
