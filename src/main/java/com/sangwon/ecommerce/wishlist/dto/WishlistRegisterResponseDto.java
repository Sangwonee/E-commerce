package com.sangwon.ecommerce.wishlist.dto;

import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import lombok.Getter;

@Getter
public class WishlistRegisterResponseDto {
    private Long itemWishlistId;
    private String itemName;
    private Integer itemPrice;

    public WishlistRegisterResponseDto(ItemWishlist savedItemWishlist) {
        this.itemWishlistId = savedItemWishlist.getId();
        this.itemName = savedItemWishlist.getItem().getName();
        this.itemPrice = savedItemWishlist.getItem().getPrice();
    }
}
