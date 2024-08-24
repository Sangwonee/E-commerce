package com.sangwon.ecommerce.order.entity;

public enum Status {
    PENDING,    // 상품 준비중
    SHIPPED,    // 배송 중
    COMPLETED,  // 베송 완료
    CANCELED,    // 주문취소
    REFUNDED       // 환불
}
