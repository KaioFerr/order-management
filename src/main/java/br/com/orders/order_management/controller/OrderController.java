package br.com.orders.order_management.controller;

import br.com.orders.order_management.domain.Order;
import br.com.orders.order_management.dto.CreateOrderRequest;
import br.com.orders.order_management.dto.OrderResponse;
import br.com.orders.order_management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController (OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder (@RequestBody CreateOrderRequest request){
        Order newOrder = orderService.createOrder(request);

        OrderResponse response = new OrderResponse();
        response.setId(newOrder.getId());
        response.setCustomerName(newOrder.getCustomerName());
        response.setOrderDateTime(newOrder.getOrderDate());
        response.setStatus(newOrder.getStatus());
        response.setTotalAmount(newOrder.getTotalAmount());

        return ResponseEntity.created(URI.create(("/api/orders/" + newOrder.getId()))).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        return orderService.findOrderById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
