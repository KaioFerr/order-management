# Sistema de Gerenciamento de Pedidos (Order Management System)

Este é um projeto de estudo para demonstrar a construção de uma aplicação backend completa com Java, Spring Boot, Docker, Redis, Apache Kafka e, futuramente, CI/CD.

[![CI Pipeline for Order Management System](https://github.com/KaioFerr/order-management/actions/workflows/ci-pipeline.yml/badge.svg)](https://github.com/KaioFerr/order-management/actions/workflows/ci-pipeline.yml)
## Tecnologias Utilizadas

* Java 17
* Spring Boot 3
* Maven
* PostgreSQL (via Docker)
* Redis (via Docker)
* Apache Kafka & Zookeeper (via Docker)
* Docker & Docker Compose

## Como Rodar o Projeto

Para executar o ambiente de desenvolvimento completo, você precisa ter o Docker e o Docker Compose instalados.

1.  **Clone o repositório:**
    ```
    git clone [https://github.com/KaioFerr/order-management.git](https://github.com/KaioFerr/order-management.git)
    ```

2.  **Navegue até a pasta do projeto:**
    ```
    cd order-management
    ```

3.  **Configure as variáveis de ambiente:**
    Copie o arquivo de exemplo `.env.example` para um novo arquivo chamado `.env`.
    ```
    # No Windows (PowerShell)
    copy .env.example .env
    
    # No Linux ou Mac
    cp .env.example .env
    ```
    Em seguida, abra o arquivo `.env` e substitua o valor da senha do banco de dados.

4.  **Execute o Docker Compose:**
    Este comando irá construir as imagens e iniciar todos os containers.
    ```
    docker-compose up --build
    ```
    A aplicação estará disponível em `http://localhost:8080`.

## Arquitetura de Eventos com Kafka

Quando um novo pedido é criado, o sistema adota uma abordagem assíncrona:

1.  A API recebe a requisição e salva o pedido no banco de dados PostgreSQL.
2.  A resposta é enviada **imediatamente** para o cliente com o status `201 Created`.
3.  **Em paralelo**, um evento `OrderEvent` é publicado no tópico Kafka `orders.new`.
4.  Um serviço consumidor (`consumer`) escuta este tópico, recebe o evento e o processa em segundo plano (atualmente, apenas registra um log).

Isso torna a API extremamente rápida para o usuário final e desacopla as responsabilidades do sistema.

## Verificando o Fluxo do Kafka

Para ver as mensagens sendo produzidas e consumidas em tempo real:

1.  **Acesse a Kafka UI**: Abra seu navegador e vá para `http://localhost:8090`.
2.  **Crie um Pedido**: Use o endpoint `POST /api/orders` para criar um novo pedido.
3.  **Veja a Mensagem**: Na Kafka UI, navegue até **Topics -> orders.new -> Messages**. Você verá a nova mensagem que foi produzida.
4.  **Verifique o Consumidor**: Nos logs da aplicação (`docker-compose logs order-app`), você verá a mensagem sendo consumida pelo `KafkaConsumerService`.

## Endpoints da API

### Criar um Pedido

Cria um novo pedido e publica um evento no Kafka.

* **URL:** `/api/orders`
* **Método:** `POST`
* **Corpo da Requisição (JSON):**
    ```
    {
      "customerName": "Cliente Kafka",
      "totalAmount": 299.99
    }
    ```

### Buscar um Pedido por ID

Busca um pedido existente. A resposta é cacheada no Redis.

* **URL:** `/api/orders/{id}`
* **Método:** `GET`
