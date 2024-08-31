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
            updateOrderStatusBasedOnAge(order);
            processRefundIfEligible(order);
        }

        orderRepository.saveAll(orders);
    }

    // 주문 상태 업데이트 로직
    private void updateOrderStatusBasedOnAge(Order order) {
        long daysSinceOrder = ChronoUnit.DAYS.between(order.getOrderDate(), LocalDateTime.now());

        if (daysSinceOrder == 1 && order.getStatus() == Status.PENDING) {
            order.updateStatus(Status.SHIPPED);
        } else if (daysSinceOrder == 2 && order.getStatus() == Status.SHIPPED) {
            order.updateStatus(Status.DELIVERED);
        }
    }

    // 반품 처리 로직
    private void processRefundIfEligible(Order order) {
        if (order.getStatus() == Status.REFUND_REQUESTED &&
                order.getRefundDate().plusDays(1).isBefore(LocalDateTime.now())) {
            order.updateStatus(Status.REFUNDED);
            order.restoreStock(order);
        }
    }

}