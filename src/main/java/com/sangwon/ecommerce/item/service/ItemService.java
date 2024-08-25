package com.sangwon.ecommerce.item.service;

import com.sangwon.ecommerce.item.dto.ItemCreateRequestDto;
import com.sangwon.ecommerce.item.dto.ItemCreateResponseDto;
import com.sangwon.ecommerce.item.dto.ItemInfoResponseDto;
import com.sangwon.ecommerce.item.dto.ItemListResponseDto;
import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemCreateResponseDto registerItem(ItemCreateRequestDto itemCreateRequestDto){
        Item item = new Item(itemCreateRequestDto);
        Item savedItem = itemRepository.save(item);
        return new ItemCreateResponseDto(savedItem);
    }

    public ItemInfoResponseDto getItem(Long id){
        Item item = itemRepository.findById(id).orElseThrow(()-> new RuntimeException("Item Not Found"));
        return new ItemInfoResponseDto(item);
    }

    public List<ItemListResponseDto> getItems(){
        List<Item> items = itemRepository.findAll();
        return items.stream().map(ItemListResponseDto::new).toList();
    }


}
