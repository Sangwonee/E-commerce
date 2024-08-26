package com.sangwon.ecommerce.wishlist.repository;

import com.sangwon.ecommerce.user.entity.User;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Optional<Wishlist> findByUser(User user);
}
