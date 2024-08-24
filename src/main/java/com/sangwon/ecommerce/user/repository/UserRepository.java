package com.sangwon.ecommerce.user.repository;

import com.sangwon.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
