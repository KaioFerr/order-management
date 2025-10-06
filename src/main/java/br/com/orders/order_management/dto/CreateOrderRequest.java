package br.com.orders.order_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequest {
    private String customerName;
    private Double totalAmount;
}
