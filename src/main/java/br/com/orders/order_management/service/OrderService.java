package br.com.orders.order_management.service;

import br.com.orders.order_management.domain.Order;
import br.com.orders.order_management.domain.OrderStatus;
import br.com.orders.order_management.dto.CreateOrderRequest;
import br.com.orders.order_management.dto.OrderEvent;
import br.com.orders.order_management.dto.OrderResponse;
import br.com.orders.order_management.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public OrderService(OrderRepository orderRepository, KafkaProducerService kafkaProducerService){
        this.orderRepository = orderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public Order createOrder(CreateOrderRequest request){
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setTotalAmount(request.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        OrderEvent event = new OrderEvent(
                savedOrder.getId(),
                savedOrder.getCustomerName(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                LocalDateTime.now()
        );
        kafkaProducerService.sendOrderEvent(event);

        return savedOrder;
    }

    @Cacheable(value = "orders", key = "#id")
    public Optional<OrderResponse> findOrderById (Long id) {
        System.out.println("Buscando pedido no BANCO DE DADOS com ID: " + id);

        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()){
            return Optional.empty();
        }

        Order order = orderOptional.get();
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerName(order.getCustomerName());
        response.setOrderDateTime(order.getOrderDate());
        response.setStatus(order.getStatus());
        response.setTotalAmount(order.getTotalAmount());
        return Optional.of(response);
    }
}
