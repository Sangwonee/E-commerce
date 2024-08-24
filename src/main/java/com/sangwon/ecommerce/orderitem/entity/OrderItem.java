package com.sangwon.ecommerce.orderitem.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
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
    private Integer totalPrice;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
