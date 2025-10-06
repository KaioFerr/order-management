package br.com.orders.order_management.dto;

import br.com.orders.order_management.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String customerName;
    private LocalDateTime orderDateTime;
    private OrderStatus status;
    private Double totalAmount;
}
