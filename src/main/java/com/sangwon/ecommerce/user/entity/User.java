package com.sangwon.ecommerce.user.entity;

import com.sangwon.ecommerce.global.audi.Timestamped;
import com.sangwon.ecommerce.order.entity.Order;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String email;
    private String name;
    private String password;
    private String address;
    private String phoneNumber;
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();
    @OneToOne(mappedBy = "user")
    private Wishlist wishlist;
}
