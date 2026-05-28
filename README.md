# Transaction System 💳

An event-driven banking transaction system built using Spring Boot, Apache Kafka, and MySQL that simulates asynchronous money transfers between accounts.

This project demonstrates how real-world fintech systems process transactions using asynchronous messaging architecture instead of tightly coupled synchronous APIs.

## Repository
https://github.com/Anamika-Sangwan/Transaction-System

---

# 🚀 Features

- Create and manage accounts
- Send money between accounts
- Asynchronous transaction processing using Kafka
- Kafka Producer & Consumer implementation
- Transaction persistence using MySQL
- Transaction status tracking
- Spring Boot REST APIs
- Layered backend architecture
- Maven-based project setup
- Event-driven system design

---

# 🏗️ Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Backend language |
| Spring Boot | REST API framework |
| Spring Data JPA | Database ORM |
| Apache Kafka | Event streaming/message queue |
| MySQL | Relational database |
| Maven | Dependency management |
| Docker | Kafka & Zookeeper setup |
| Postman | API testing |

---

# 📂 Project Structure

```text
src/main/java/com/anamika/transactionsystem

├── controller
├── service
├── repository
├── entity
├── dto
├── kafka
├── event
├── constants
└── config
```

---

# ⚙️ System Flow

```text
Client Request
      ↓
Transaction Controller
      ↓
Kafka Producer
      ↓
Kafka Topic ("transactions")
      ↓
Kafka Consumer
      ↓
Transaction Service
      ↓
MySQL Database
```

---

# 🔥 Key Architecture Concept

Instead of directly processing transactions inside the API request thread:

```text
API → Kafka → Consumer → Database
```

This makes the system:
- More scalable
- Fault tolerant
- Asynchronous
- Similar to real fintech transaction systems

---

# 🛠️ Setup Instructions

## 1️⃣ Clone the Repository

```bash
git clone https://github.com/Anamika-Sangwan/Transaction-System.git
cd Transaction-System
```

---

# 2️⃣ Start Kafka using Docker

Create a `docker-compose.yml` file:

```yaml
version: '3'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:7.5.0
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181

  kafka:
    image: confluentinc/cp-kafka:7.5.0
    ports:
      - "9092:9092"
    environment:
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
```

Run:

```bash
docker compose up -d
```

---

# 3️⃣ Configure MySQL

Create database:

```sql
CREATE DATABASE transaction_system;
```

Update `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/transaction_system
    username: root
    password: yourpassword

  jpa:
    hibernate:
      ddl-auto: update

  kafka:
    bootstrap-servers: localhost:9092
```

---

# 4️⃣ Build the Project

```bash
mvn clean install
```

---

# 5️⃣ Run the Application

```bash
mvn spring-boot:run
```

---

# 📬 API Endpoints

## Create Account

```http
POST /account/create
```

### Request

```json
{
  "accountNumber": "ACC001",
  "balance": 1000
}
```

---

## Send Money

```http
POST /transaction/send
```

### Request

```json
{
  "senderAccountId": 1,
  "receiverAccountId": 2,
  "amount": 100
}
```

### Response

```json
{
  "status": "PENDING",
  "message": "Transaction is being processed"
}
```

---

# 🧠 Kafka Processing

When `/transaction/send` is called:

1. API publishes a `TransactionEvent`
2. Kafka stores the event in a topic
3. Consumer listens to the topic
4. Consumer processes transaction asynchronously
5. Database updates balances and transaction history

---

# 📸 Example Transaction Flow

```text
Account A → Kafka Event → Consumer → Account B
```

---

# 🔍 Future Improvements

- Transaction status API
- Dead Letter Queue (DLQ)
- Retry mechanism
- Authentication & Authorization
- Swagger/OpenAPI documentation
- Dockerized full deployment
- Microservices architecture
- Redis caching
- Notification service
- Unit & Integration testing

---

# 📚 Learning Outcomes

This project helped explore:
- Event-driven architecture
- Kafka Producer/Consumer model
- Async backend systems
- Distributed transaction flow
- Spring Boot backend development
- Database transaction handling

---

# 👩‍💻 Author

Created by Anamika Sangwan

GitHub:
https://github.com/Anamika-Sangwan

LinkedIn:
https://in.linkedin.com/in/anamika-sangwan-985463243

---

# ⭐ If you like this project

Consider starring the repository on GitHub 🙌
