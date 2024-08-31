package com.sangwon.ecommerce.orderitem.entity;

import com.sangwon.ecommerce.global.audit.Timestamped;
import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.order.entity.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;
    private Integer quantity;
    private Integer orderPrice;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    public OrderItem(Order order, Item item, Integer quantity) {
        this.order = order;
        this.item = item;
        this.quantity = quantity;
        this.orderPrice = item.getPrice() * quantity;
    }
}
