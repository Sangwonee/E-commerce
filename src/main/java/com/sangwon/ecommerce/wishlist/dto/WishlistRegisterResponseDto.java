package com.sangwon.ecommerce.wishlist.dto;

import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import lombok.Getter;

@Getter
public class WishlistRegisterResponseDto {
    private Long itemWishlistId;
    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer itemQuantity;

    public WishlistRegisterResponseDto(ItemWishlist savedItemWishlist) {
        this.itemWishlistId = savedItemWishlist.getId();
        this.itemId = savedItemWishlist.getId();
        this.itemName = savedItemWishlist.getItem().getName();
        this.itemPrice = savedItemWishlist.getItem().getPrice();
        this.itemQuantity = savedItemWishlist.getItemQuantity();
    }
}
