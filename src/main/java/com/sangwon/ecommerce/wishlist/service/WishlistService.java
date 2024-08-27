package com.sangwon.ecommerce.wishlist.service;

import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.item.repository.ItemRepository;
import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import com.sangwon.ecommerce.itemwishlist.repository.ItemWishlistRepository;
import com.sangwon.ecommerce.user.entity.User;
import com.sangwon.ecommerce.user.repository.UserRepository;
import com.sangwon.ecommerce.wishlist.dto.*;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import com.sangwon.ecommerce.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {
    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemWishlistRepository itemWishlistRepository;

    @Transactional
    public WishlistRegisterResponseDto addItemToWishlist(WishlistRegisterRequestDto wishlistRegisterRequestDto, Long userId) {
        User user = findByUserId(userId);
        Item item = findItemById(wishlistRegisterRequestDto.getItemId());
        Wishlist wishlist = wishlistRepository.findByUser(user).orElse(new Wishlist(user));
        if (wishlist.getId() == null) {
            wishlist = wishlistRepository.save(wishlist);
        }
        ItemWishlist itemWishlist = new ItemWishlist(wishlistRegisterRequestDto, item, wishlist);
        ItemWishlist savedItemWishlist = itemWishlistRepository.save(itemWishlist);

        return new WishlistRegisterResponseDto(savedItemWishlist);
    }

    @Transactional
    public WishlistUpdateResponseDto updateWishlist(WishlistUpdateRequestDto wishlistUpdateRequestDto, Long userId) {
        User user = findByUserId(userId);
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(()->new RuntimeException("Wishlist not found"));
        ItemWishlist itemWishlist = itemWishlistRepository.findByWishlistAndItemId(wishlist, wishlistUpdateRequestDto.getItemId())
                .orElseThrow(()->new RuntimeException("ItemWishlist not found"));

        itemWishlist.updateQuantity(wishlistUpdateRequestDto);

        return new WishlistUpdateResponseDto(itemWishlist);
    }

    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishlist(Long userId) {
        User user = findByUserId(userId);
        Wishlist wishlist = findWishlistByUser(user);
        List<ItemWishlist> itemWishlist = itemWishlistRepository.findByWishlist(wishlist);

        return itemWishlist.stream().map(WishlistResponseDto::new).toList();
    }

    @Transactional
    public void deleteItemFromWishlist(Long itemId, Long userId) {
        User user = findByUserId(userId);
        Item item = findItemById(itemId);
        Wishlist wishlist = findWishlistByUser(user);

        itemWishlistRepository.deleteByWishlistAndItem(wishlist, item);
    }

    private User findByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));
    }

    private Wishlist findWishlistByUser(User user) {
        return wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not Found"));
    }
}
