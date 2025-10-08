package br.com.orders.order_management;


import br.com.orders.order_management.repository.OrderRepository;
import br.com.orders.order_management.service.KafkaConsumerService;
import br.com.orders.order_management.service.KafkaProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class OrderManagementApplicationTests {

	// Diz ao Spring: "Para este teste, não use o OrderRepository de verdade.
	// Crie uma versão falsa no lugar dele."
	@MockBean
	private OrderRepository orderRepository;

	// Faz o mesmo para os serviços do Kafka, que também dependem de uma conexão externa.
	@MockBean
	private KafkaProducerService kafkaProducerService;

	@MockBean
	private KafkaConsumerService kafkaConsumerService;


	@Test
	void contextLoads() {
		// Este teste agora verifica se a aplicação consegue iniciar
		// usando as "ferramentas de mentira" que nós fornecemos.
		// Como as ferramentas falsas não precisam de conexão, o teste vai passar.
	}
}
}
