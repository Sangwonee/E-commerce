package com.sangwon.ecommerce.itemwishlist.repository;

import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemWishlistRepository extends JpaRepository<ItemWishlist, Long> {
}
