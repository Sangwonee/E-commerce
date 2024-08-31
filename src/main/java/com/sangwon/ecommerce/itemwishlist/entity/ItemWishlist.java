package com.sangwon.ecommerce.itemwishlist.entity;

import com.sangwon.ecommerce.global.audit.Timestamped;
import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.wishlist.dto.WishlistRegisterRequestDto;
import com.sangwon.ecommerce.wishlist.dto.WishlistUpdateRequestDto;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "item_wishlist")
public class ItemWishlist extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_wishlist_id")
    private Long id;
    private Integer itemQuantity;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    public ItemWishlist(WishlistRegisterRequestDto requestDto, Item item, Wishlist wishlist) {
        this.itemQuantity = requestDto.getItemQuantity();
        this.item = item;
        this.wishlist = wishlist;
    }

    public void updateQuantity(WishlistUpdateRequestDto wishlistUpdateRequestDto) {
        this.itemQuantity = wishlistUpdateRequestDto.getQuantity();
    }
}
