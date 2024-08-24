package com.sangwon.ecommerce.item.repository;

import com.sangwon.ecommerce.item.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
