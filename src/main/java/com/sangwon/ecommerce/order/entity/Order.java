package com.sangwon.ecommerce.order.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
import com.sangwon.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "orders")
public class Order extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private LocalDate orderDate;
    private Status status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
