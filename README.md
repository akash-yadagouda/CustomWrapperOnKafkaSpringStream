# Spring Streams Pub/Sub Framework

A modular and lightweight **Publish-Subscribe framework** built on **Spring Cloud Stream** and **Kafka Streams**, designed for distributed microservices and real-time event processing.

---

## ✨ Features

- ⚙️ Kafka-based event streaming using Spring Cloud Stream (Functional Model)
- 📦 Clean abstraction for publishing and subscribing to events
- 🔁 Reusable `EventPublisher` component
- 🔧 Plug and play in any microservice
- 🧪 Extendable and testable architecture

---

## 📁 Project Structure

```
spring-streams-pubsub-framework/
├── src/
│   └── main/
│       ├── java/com/example/pubsubframework/
│       │   └── PubSubFramework.java
│       └── resources/
│           └── application.yml
├── pom.xml
└── README.md
```

---

## 🚀 Getting Started

### ✅ Prerequisites

- Java 17+
- Apache Kafka running locally (`localhost:9092`)
- Maven

### 🛠️ Build and Run

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run
```

---

## 📨 Usage

### Publishing an Event

```java
@Autowired
private EventPublisher publisher;

publisher.publish(new PubSubEvent("USER_CREATED", "{\"id\":123}"));
```

### Subscribing to Events

Modify the `ExampleSubscriber` class to handle your domain-specific events.

```java
@StreamListener(Processor.INPUT)
public void handleEvent(PubSubEvent event) {
    // Your logic here
}
```

---

## ⚙️ Configuration (`application.yml`)

```yaml
spring:
  cloud:
    stream:
      bindings:
        pubSubProcessor-in-0:
          destination: pubsub-topic
        pubSubProcessor-out-0:
          destination: pubsub-topic
      kafka:
        binder:
          brokers: localhost:9092
```

---

## 🧠 Design Patterns Used

- **Event-driven Architecture**
- **Publisher/Subscriber Pattern**
- **Separation of Concerns**
- **Functional Configuration (Spring Streams)**

---

## 📦 Future Enhancements

- Add support for message filtering and transformation
- Enable multi-topic subscription support
- Add metrics and tracing support (Micrometer, Sleuth)

---

## 📜 License

MIT License – use this framework freely in your projects 🚀

---

## 🤝 Contributing

Contributions are welcome! Fork the repo and raise a PR.
