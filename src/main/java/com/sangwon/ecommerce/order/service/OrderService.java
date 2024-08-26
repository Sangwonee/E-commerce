package com.sangwon.ecommerce.order.service;

import com.sangwon.ecommerce.item.entity.Item;
import com.sangwon.ecommerce.item.repository.ItemRepository;
import com.sangwon.ecommerce.order.dto.OrderCreateRequestDto;
import com.sangwon.ecommerce.order.dto.OrderCreateResponseDto;
import com.sangwon.ecommerce.order.dto.OrderGetStatusResponseDto;
import com.sangwon.ecommerce.order.entity.Order;
import com.sangwon.ecommerce.order.entity.Status;
import com.sangwon.ecommerce.order.repository.OrderRepository;
import com.sangwon.ecommerce.orderitem.entity.OrderItem;
import com.sangwon.ecommerce.orderitem.repository.OrderItemRepository;
import com.sangwon.ecommerce.user.entity.User;
import com.sangwon.ecommerce.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderCreateResponseDto createOrder(OrderCreateRequestDto orderCreateRequestDto, Long userId) {
        // todo : 재고가 남아있는지 확인한후 주문 가능
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not Found"));
        Item item = itemRepository.findById(orderCreateRequestDto.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        Order order = new Order(user);
        Order savedOrder = orderRepository.save(order);

        OrderItem orderItem = new OrderItem(savedOrder, item, orderCreateRequestDto);
        OrderItem saveedOrderItem = orderItemRepository.save(orderItem);

        item.quantity(saveedOrderItem);

        return new OrderCreateResponseDto(saveedOrderItem);
    }


    @Transactional(readOnly = true)
    public OrderGetStatusResponseDto getOrderStatus(Long orderId) {
        Order order = findByOrderId(orderId);
        return new OrderGetStatusResponseDto(order);
    }


    public void cancel(Long orderId){
        Order order = findByOrderId(orderId);
        if(order.getStatus() == Status.PENDING){
            order.cancelOrder();
            // todo : 취소한 수량 만큼 item 수량 추가해야함
        }

    }

    public void refund(Long orderId){
        Order order = findByOrderId(orderId);
        order.refundOrder();
    }



    private Order findByOrderId(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not Found"));
    }
}
