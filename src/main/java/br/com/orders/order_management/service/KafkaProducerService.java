package br.com.orders.order_management.service;

import br.com.orders.order_management.dto.OrderEvent;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;


@Service
public class KafkaProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;
    private static final String TOPIC = "orders.new";


    public KafkaProducerService(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        LOGGER.info(String.format("Produzindo mensagem de evento de pedido -> %s", orderEvent));
        kafkaTemplate.send(TOPIC, String.valueOf(orderEvent.getOrderId()), orderEvent);
    }
}
