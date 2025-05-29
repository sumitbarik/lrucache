Project Overview:
------------------
This project implements a robust and scalable LRU (Least Recently Used) Cache with the following key features:

In-Memory Cache Implementation:
- Utilizes Java's LinkedHashMap with access-order maintenance
- O(1) time complexity for get/put operations
- Configurable maximum cache size with automatic eviction
- Thread-safe implementation using synchronization

Persistence Layer:
- H2 Database integration for persistent storage
- Automatic cache warm-up on application restart
- Asynchronous write-through caching strategy
- Configurable persistence settings
- ACID compliance for cache operations

Architecture & Design:
- Clean architecture with separation of concerns
- Service layer abstraction for business logic
- Repository pattern for data access
- Builder pattern for cache configuration
- Factory pattern for cache initialization

Spring Boot Integration:
- RESTful API endpoints for cache operations
- Actuator endpoints for monitoring
- Swagger UI for API documentation
- Spring profiles for different environments
- Embedded Tomcat server

Features:
- Key-value pair storage with generic type support
- Automatic eviction of least recently used items
- Cache statistics and metrics
- Error handling with proper HTTP status codes
- Logging and monitoring capabilities

Prerequisites:
-------------
- Java 11 or higher
- Maven 3.6 or higher
- Available port 8080

To build:
----------
mvn clean install

Execute:
---------
java -jar target/lrucache-0.0.1-SNAPSHOT.jar


API Documentation:
-----------------
The following REST endpoints are available:

- PUT /api/cache/{key}
  - Stores a value in the cache
  - Request body: JSON value
  - Returns: 200 OK on success

- GET /api/cache/{key}
  - Retrieves a value from cache
  - Returns: 200 OK with value or 404 if not found

- DELETE /api/cache/{key}
  - Removes an entry from cache
  - Returns: 204 No Content

Configuration:
-------------
The following properties can be configured in application.properties:

- cache.size.max=1000 (Maximum number of entries in cache)
- cache.persistence.enabled=true (Enable/disable H2 persistence)
- server.port=8080 (Application port)

H2 Console:
-----------
The H2 database console is available at:
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:file:./data/cache
Username: sa
Password: password

