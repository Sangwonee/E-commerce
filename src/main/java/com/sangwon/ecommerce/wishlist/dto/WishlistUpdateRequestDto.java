package com.sangwon.ecommerce.wishlist.dto;

import lombok.Getter;

@Getter
public class WishlistUpdateRequestDto {
    private Long itemId;
    private Integer quantity;
}
