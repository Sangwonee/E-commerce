package com.sangwon.ecommerce.orderitem.repository;

import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
