package br.com.orders.order_management.dto;

import br.com.orders.order_management.domain.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent implements Serializable {
    private Long orderId;
    private String customerName;
    private Double totalAmount;
    private OrderStatus status;
    private LocalDateTime eventDate;
}
