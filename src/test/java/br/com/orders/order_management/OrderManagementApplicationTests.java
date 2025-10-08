package br.com.orders.order_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
class OrderManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}
