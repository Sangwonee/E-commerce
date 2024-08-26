package com.sangwon.ecommerce.wishlist.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import com.sangwon.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wishlists")
public class Wishlist extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;
    private Integer itemQuantity;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "wishlist")
    private List<ItemWishlist> itemWishlists = new ArrayList<>();

    public Wishlist(User user) {
        this.user = user;
    }
}
