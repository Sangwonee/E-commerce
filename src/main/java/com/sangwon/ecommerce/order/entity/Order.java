package com.sangwon.ecommerce.order.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
import com.sangwon.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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
        this.status = Status.REFUNDED;
    }
}
