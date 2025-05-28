Project Overview

In-Memory LRU Cache: Utilizes LinkedHashMap to maintain access order
Persistence Layer: Uses H2 DB for storing cache entries on the filesystem.
Spring Boot Integration: Provides RESTful APIs to interact with the cache.
Maven Build: Configured with necessary dependencies for Spring Boot and H2 DB.

To build:
mvn clean install

Execute:
java -jar target/lrucache-0.0.1-SNAPSHOT.jar

