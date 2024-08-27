package com.sangwon.ecommerce.wishlist.dto;

import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import lombok.Getter;

@Getter
public class WishlistUpdateResponseDto {
    private Long itemId;
    private Integer quantity;

    public WishlistUpdateResponseDto(ItemWishlist updatedItemwishlist) {
        this.itemId = updatedItemwishlist.getItem().getId();
        this.quantity = updatedItemwishlist.getItemQuantity();
    }
}
