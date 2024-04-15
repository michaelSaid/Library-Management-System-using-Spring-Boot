# Library-Management System using Spring Boot
## Introduction
A Library Management System designed to see the books available in a library. It allows patrons to borrow/return books from the library. The backend is designed as a **Monolithic Architecture** with various nuances as discussed below.
## Technologies and Dependencies Used
* [Maven](https://maven.apache.org/) used as a dependency management tool.
* [Spring Boot](https://spring.io/projects/spring-boot) used to build hassle free web applications and writing REST APIs.
* [Spring Security](https://spring.io/projects/spring-security) used for Authentication and Authorizations.
* [Spring data JPA (Hibernate)](https://hibernate.org/) Used to reduce the time of writing hardcoded sql queries and instead allows to write much more readable and scalable code
* [MySQL](https://www.mysql.com/) used as a Java persistence store
* [Project Lombok](https://projectlombok.org/) Reduces the time  of writing java boiler plate code.

## Using Library Management System
CLI-->
```
git clone https://github.com/michaelSaid/Library-Management-System-using-Spring-Boot.git
cd Library-Management-System-using-Spring-Boot
mvn package 
java -jar target/librarymanagementsystem-0.0.1-SNAPSHOT.jar
```
Intellij/Eclipse-->
1. Let maven resolve dependencies
2. run SpringBootApplication

## Backend Design
### Entities
Actors/Entities are inspired by the real world entities that can use the applications
1. **Book** having attributes:
* id (unique primary key), title, description, author, ISBN, publication year, quantity.
2. **Patron** having attributes:
* id (unique primary key), name, address, phoneNumber, emailAddress.
3. **Borrow** having attributes:
* id (unique primary key) book_id (foreign key), patron_id (foreign key), borrowing date, return date.