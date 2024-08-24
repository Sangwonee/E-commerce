package com.sangwon.ecommerce.wishlist.repository;

import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
