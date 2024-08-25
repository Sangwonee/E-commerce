package com.sangwon.ecommerce.item.controller;

import com.sangwon.ecommerce.item.dto.ItemCreateRequestDto;
import com.sangwon.ecommerce.item.dto.ItemCreateResponseDto;
import com.sangwon.ecommerce.item.dto.ItemInfoResponseDto;
import com.sangwon.ecommerce.item.dto.ItemListResponseDto;
import com.sangwon.ecommerce.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemCreateResponseDto> registerItem(@RequestBody ItemCreateRequestDto itemCreateRequestDto) {
        ItemCreateResponseDto itemCreateResponseDto = itemService.registerItem(itemCreateRequestDto);
        return new ResponseEntity<>(itemCreateResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemInfoResponseDto> getItem(@PathVariable Long itemId) {
        ItemInfoResponseDto itemInfoResponseDto = itemService.getItem(itemId);
        return new ResponseEntity<>(itemInfoResponseDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ItemListResponseDto>> getItems() {
        List<ItemListResponseDto> itemListResponseDto = itemService.getItems();
        return new ResponseEntity<>(itemListResponseDto, HttpStatus.OK);
    }
}
