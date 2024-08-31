package com.sangwon.ecommerce.order.service;

import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.itemwishlist.entity.ItemWishlist;
import com.sangwon.ecommerce.itemwishlist.repository.ItemWishlistRepository;
import com.sangwon.ecommerce.order.dto.OrderCreateResponseDto;
import com.sangwon.ecommerce.order.dto.OrderGetStatusResponseDto;
import com.sangwon.ecommerce.order.entity.Order;
import com.sangwon.ecommerce.order.entity.Status;
import com.sangwon.ecommerce.order.repository.OrderRepository;
import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import com.sangwon.ecommerce.user.entity.User;
import com.sangwon.ecommerce.user.repository.UserRepository;
import com.sangwon.ecommerce.wishlist.entity.Wishlist;
import com.sangwon.ecommerce.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private final ItemWishlistRepository itemWishlistRepository;

    @Transactional
    public OrderCreateResponseDto createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));
        Wishlist wishlist = wishlistRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Wishlist not found"));

        if (wishlist.isEmpty()) {
            throw new IllegalArgumentException("Wishlist is empty");
        }

        Order order = createOrderFromWishlist(user, wishlist);
        Order savedOrder = orderRepository.save(order);

        itemWishlistRepository.deleteAll(wishlist.getItemWishlists());

        return new OrderCreateResponseDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderGetStatusResponseDto getOrderStatus(Long orderId) {
        Order order = findByOrderId(orderId);
        return new OrderGetStatusResponseDto(order);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findByOrderId(orderId);
        if (order.getStatus() == Status.PENDING) {
            order.cancelOrder();
            order.restoreStock(order);
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("주문 취소가 불가능합니다.");
        }
    }

    @Transactional
    public void refundOrder(Long orderId) {
        Order order = findByOrderId(orderId);
        validateRefundConditions(order);

        order.refundOrder();

        orderRepository.save(order);
    }


    private Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not Found"));
    }

    private Order createOrderFromWishlist(User user, Wishlist wishlist) {
        Order order = new Order(user);
        for (ItemWishlist itemWishlist : wishlist.getItemWishlists()) {
            Item item = itemWishlist.getItem();
            Integer quantity = itemWishlist.getItemQuantity();

            item.reduceStock(quantity);

            OrderItem orderItem = new OrderItem(order, item, quantity);
            order.addOrderItem(orderItem);
        }
        return order;
    }

    private void validateRefundConditions(Order order) {
        if (order.getStatus() != Status.DELIVERED) {
            throw new IllegalArgumentException("반품이 가능한 상태가 아닙니다.");
        }

        LocalDateTime orderDate = order.getOrderDate();
        LocalDateTime now = LocalDateTime.now();
        long daysSinceOrder = ChronoUnit.DAYS.between(orderDate, now);

        if (daysSinceOrder > 3) {
            throw new IllegalArgumentException("반품 기한이 지나 반품이 불가능합니다.");
        }
    }


}