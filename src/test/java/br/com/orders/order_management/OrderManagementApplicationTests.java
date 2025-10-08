package br.com.orders.order_management;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
		"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration",
		"spring.data.redis.repositories.enabled=false",
		"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"
})
class OrderManagementApplicationTests {

	@Test
	void contextLoads() {
	}

}
