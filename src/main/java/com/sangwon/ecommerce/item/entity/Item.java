package com.sangwon.ecommerce.item.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
import com.sangwon.ecommerce.item.dto.ItemCreateRequestDto;
import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "items")
public class Item extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Integer price;
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<ItemWishlist> itemWishlists = new ArrayList<>();

    public Item(ItemCreateRequestDto itemCreateRequestDto) {
        this.name = itemCreateRequestDto.getName();
        this.description = itemCreateRequestDto.getDescription();
        this.stock = itemCreateRequestDto.getStock();
        this.price = itemCreateRequestDto.getPrice();
    }

    public void reduceStock(Integer quantity){
        this.stock -= quantity;
    }

    public void addStock(Integer quantity) {
        this.stock += quantity;
    }
}
