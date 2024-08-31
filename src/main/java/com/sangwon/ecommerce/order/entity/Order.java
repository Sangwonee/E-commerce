package com.sangwon.ecommerce.order.entity;

import com.sangwon.ecommerce.global.audit.Timestamped;
import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import com.sangwon.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private LocalDateTime orderDate;
    private LocalDateTime refundDate;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(User user) {
        this.orderDate = LocalDateTime.now();
        this.status = Status.PENDING;
        this.user = user;
    }

    public void cancelOrder(){
        this.status = Status.CANCELED;
    }

    public void updateStatus(Status newStatus) {
        this.status = newStatus;
    }

    public void refundOrder(){
        this.refundDate = LocalDateTime.now();
        this.status = Status.REFUND_REQUESTED;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public void restoreStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Item item = orderItem.getItem();
            Integer quantity = orderItem.getQuantity();
            item.addStock(quantity);
        }
    }
}
