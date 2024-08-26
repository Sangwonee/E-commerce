package com.sangwon.ecommerce.order.scheduler;

import com.sangwon.ecommerce.order.entity.Order;
import com.sangwon.ecommerce.order.entity.Status;
import com.sangwon.ecommerce.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {
    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateOrderStatus() {
        List<Order> orders = orderRepository.findAll();

        for (Order order : orders) {
            LocalDateTime orderDate = order.getOrderDate();
            long daysSinceOrder = ChronoUnit.DAYS.between(orderDate, LocalDateTime.now());

            if (daysSinceOrder == 1 && order.getStatus() == Status.PENDING) {
                order.updateStatus(Status.SHIPPED);
            } else if (daysSinceOrder == 2 && order.getStatus() == Status.SHIPPED) {
                order.updateStatus(Status.DELIVERED);
            }
        }

        orderRepository.saveAll(orders);
    }
}