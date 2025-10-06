package br.com.orders.order_management.service;

import br.com.orders.order_management.dto.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerService.class);

    @KafkaListener(topics = "order.new", groupId = "order-management-group")
    public void consumerOrderEvent(OrderEvent orderEvent) {
        LOGGER.info("Mensagem de evento de pedido recebida (consumida) -> {}}", orderEvent.getOrderId());
    }
}
