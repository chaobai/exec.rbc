### Summary
This is a small project to build up a service for fruit basket total price calculation. The functionality is elaborated a little and allowing price update, add / remove items from basket and supports multiple baskets as well.
To keep it simple, no database is used, Spring Boot is used to provide a quick solution for stand-alone service. Swagger UI provides a friendly interface for developer to interact with the service. And BDD test is added, supported by Cucumber.
Given time is limited, there isn't much description and comments in the code base, logic is simple and they should be pretty self-explanatory by itself.

### Test

This project relies on maven, tested on Maven 3.3.3, and JDK 1.7.0_79.

You will need to have access to maven repository http://repo.maven.apache.org/maven2/

To start the standalone RESTful service
```sh
$ mvn clean spring-boot:run
```

RESTful API can be accessed via Swagger UI
```sh
http://localhost:8080/swagger-ui.html
```
BDD test script can be changed for simple test. They are located under \src\test\resources\org\ruthie\exec\rbc\integration\.

To run integration test only
```sh
$ mvn clean integration-test
```

To run unit test only
```sh
$ mvn clean test
```

### Configuration

There's an configuration file called application.properties in \src\main\resources folder, by default server port is 8080

### Contact

Please feel free to drop an email to chaobai@gmail.com in case any questions. Thanks.