# Proyecto de Microservicios con Spring Boot

Este proyecto es una arquitectura de microservicios implementada con **Spring Boot** y una variedad de tecnologías y herramientas que facilitan la seguridad, la gestión de usuarios, la resiliencia, el monitoreo y la orquestación de servicios.

## Características Principales

- **Seguridad**: Implementación de autenticación y autorización con **Spring Security**, **Keycloak** (OpenID y OAuth2).
- **Balanceo de Carga**: Administración de servicios y balanceo de carga con **Eureka** de Netflix.
- **Resiliencia**: Gestión de fallos y resiliencia de los servicios con **Resilience4j**.
- **Mensajería Asíncrona**: Comunicación entre servicios mediante **Apache Kafka**.
- **Monitoreo y Métricas**: Integración con **Grafana** y **Prometheus** para el monitoreo en tiempo real.
- **Base de Datos**: Almacenamiento de datos en bases de datos relacionales (ej. MySQL, PostgreSQL).

## Requisitos

Para ejecutar este proyecto, asegúrate de tener los siguientes elementos instalados:

- **Java** 17+
- **Maven** 3.6+
- **Docker** y **Docker Compose** 

## Arquitectura del Proyecto

Este proyecto sigue una arquitectura de microservicios distribuida en la cual cada componente cumple una función específica:

- **Autenticación y Autorización**: Utiliza Keycloak y el protocolo OpenID/OAuth2 para asegurar que solo usuarios autenticados puedan acceder a los servicios.
- **Servicio de Descubrimiento y Balanceo de Carga**: Eureka actúa como servidor de descubrimiento, permitiendo que los microservicios registren su ubicación y soportando balanceo de carga.
- **Resiliencia y Tolerancia a Fallos**: Resilience4j se encarga de implementar patrones como `circuit breaker`, `retry` y `rate limiter` para garantizar la estabilidad de los servicios.
- **Mensajería y Procesamiento Asíncrono**: Kafka permite la comunicación entre microservicios mediante mensajes asíncronos, útil para eventos y procesamiento en tiempo real.
- **Monitoreo y Métricas**: Grafana y Prometheus recogen métricas de los servicios para ofrecer visibilidad y análisis del rendimiento.
