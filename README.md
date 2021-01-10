# warehouse-service

This service adds the warehouse functionality. It uploads data from json files to database on start of application and allows to perform warehouse operations.

The endpoints exposed are:

<B>1)GetProducts</B> - This returns all the available products and calculates quantity on the basis of available articles.

**2)SellProduct** - This requires two parameters to be passed product name and quantity. This reduces the articles required to create the product and returns the quantity left for the product.

It's an spring boot application. It can be started running the file WarehouseServiceApplication.

Using in-memory database H2 to save data using Hibernate framework, URL http://localhost:8080/h2-console

Added Swagger-UI to test the endpoints, URL http://localhost:8080/swagger-ui/

For metrics using spring actuator, URL http://localhost:8080/actuator/metrics

Technology Stack Used:
- Spring Boot 2.4.1
- Java 8
- Junit
- Jackson
- Swagger 
- JPA (Hibernate)
- H2
- Spring Boot Actuator


