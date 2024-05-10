The aim of this project is to learn how to use Zipkin in Spring Boot 3 Microservices

## Installation

- Configure otel-collector.yml for Using Zipkin

```
receivers:
  otlp:
    protocols:
      http:

processors:
  batch:

exporters:

  zipkin:
    endpoint: "http://zipkin:9411/api/v2/spans"
    format: proto

extensions:
  health_check:
  pprof:
  zpages:

service:
  extensions: [health_check, pprof, zpages]
  pipelines:
    traces:
      receivers: [otlp]
      processors: [batch]
      exporters: [zipkin]
```


- Run docker-compose up -d

```
version: '3'
services:
  otel-collector:
    container_name: otel-collector
    image: otel/opentelemetry-collector-contrib:0.82.0
    restart: always
    command:
      - --config=/etc/otelcol-contrib/otel-collector.yml
    volumes:
      - ./otel-collector.yml:/etc/otelcol-contrib/otel-collector.yml
    ports:
      - "1888:1888" # pprof extension
      - "8888:8888" # Prometheus metrics exposed by the collector
      - "8889:8889" # Prometheus exporter metrics
      - "13133:13133" # health_check extension
      - "4317:4317" # OTLP gRPC receiver
      - "4318:4318" # OTLP http receiver
      - "55679:55679" # zpages extension
    depends_on:
      - zipkin

  zipkin:
    container_name: zipkin
    image: openzipkin/zipkin:latest
    restart: always
    ports:
      - "9411:9411"
```

- We define the necessary dependencies (for User-Service)
```java
 </dependency>
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-tracing-bridge-otel</artifactId>
        </dependency>

        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-otlp</artifactId>
        </dependency>

        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-otlp</artifactId>
            <scope>runtime</scope>
        </dependency>

```

- We define the necessary dependencies (for Post-Service)

```java
        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-tracing-bridge-otel</artifactId>
        </dependency>

        <dependency>
            <groupId>io.opentelemetry</groupId>
            <artifactId>opentelemetry-exporter-otlp</artifactId>
        </dependency>

        <dependency>
            <groupId>io.micrometer</groupId>
            <artifactId>micrometer-registry-otlp</artifactId>
            <scope>runtime</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <dependency>
            <groupId>io.github.openfeign</groupId>
            <artifactId>feign-micrometer</artifactId>
        </dependency>

```



## Configuration
 
- First, we make the necessary edits to the application.properties files

```java
management.endpoints.web.exposure.include=health
management.otlp.tracing.endpoint=http://localhost:4318/v1/traces
management.tracing.sampling.probability=1.0
```

- Secondly, we make the necessary arrangements to create Span for the TraceConfiguration object

   [TraceConfiguration](https://github.com/muharremkoc/spring-boot-3-zipkin-learn/blob/master/user-service/src/main/java/com/spring/service/userservice/config/TraceConfiguration.java)


## Using

- Get Request "http://localhost:8089/user/{id}"

![Zipkin_User_One](https://github.com/muharremkoc/spring-boot-3-zipkin-learn/assets/80245013/9b70b3cb-1554-435b-8d2d-a324e7f8bf0f)


- Post Request "http://localhost:8090/post?platform=platform-value&userId=userId-value"

![Post_Service_One](https://github.com/muharremkoc/spring-boot-3-zipkin-learn/assets/80245013/3284bcb5-3cb0-4586-a1d1-f6173be92ff8)


[Muharrem Koç](https://github.com/muharremkoc)
