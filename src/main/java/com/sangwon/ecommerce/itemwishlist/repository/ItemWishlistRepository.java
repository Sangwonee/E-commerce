package com.sangwon.ecommerce.itemwishlist.repository;

import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemWishlistRepository extends JpaRepository<ItemWishlist, Long> {
    List<ItemWishlist> findByWishlist(Wishlist wishlist);
    void deleteByWishlistAndItem(Wishlist wishlist, Item item);

    Optional<ItemWishlist> findByWishlistAndItemId(Wishlist wishlist, Long itemId);
}