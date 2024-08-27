package com.sangwon.ecommerce.wishlist.controller;

import com.sangwon.ecommerce.wishlist.dto.*;
import com.sangwon.ecommerce.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlists")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping
    public ResponseEntity<WishlistRegisterResponseDto> addItemToWishlist(@RequestBody WishlistRegisterRequestDto wishlistRegisterRequestDto,
                                                                         @RequestHeader Long userId) {
        WishlistRegisterResponseDto wishlistRegisterResponseDto = wishlistService.addItemToWishlist(wishlistRegisterRequestDto, userId);
        return new ResponseEntity<>(wishlistRegisterResponseDto, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<WishlistUpdateResponseDto> updateWishlist(@RequestBody WishlistUpdateRequestDto wishlistUpdateRequestDto,
                                                                    @RequestHeader Long userId) {
        WishlistUpdateResponseDto wishlistUpdateResponseDto = wishlistService.updateWishlist(wishlistUpdateRequestDto, userId);
        return new ResponseEntity<>(wishlistUpdateResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getWishlist(@RequestHeader Long userId) {
        List<WishlistResponseDto> wishlistResponseDto = wishlistService.getWishlist(userId);
        return new ResponseEntity<>(wishlistResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<Void> deleteItemFromWishlist(@PathVariable Long itemId, @RequestHeader Long userId) {
        wishlistService.deleteItemFromWishlist(itemId, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
