
# SCHOOL EXPENDITURE MANAGEMENT

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.1-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-purple)

A robust backend system for managing school finances with dual-approval workflows, real-time budget tracking, and secure access control.
## Features

### Core Functionality
- ✅ **Dual-Approval Workflow**  
  Department Head → Finance Officer sequential approval
- 📊 **Real-Time Budget Tracking**  
  Department-level spending vs. allocation
- 🔐 **Role-Based Access Control**  
  Admin, Finance, Department Heads, Teachers
### Technical Highlights
- JWT Authentication & Authorization
- Audit Logging for all financial actions
- Flyway Database Migrations
- RESTful API with OpenAPI Documentation



## Installation
### Prerequisites
- Java 17

- PostgreSQL 15

- Maven 3.8+

### Steps
- Clone the repository

```
 git clone https://github.com/Olatomiw/School_Expenditure_Management.git
```

- Configure your database, flyway migration and jwt in your Application.properties

```
spring.datasource.url= ****
spring.datasource.username= ****
spring.datasource.password= ****
spring.jpa.hibernate.ddl-auto=validate

---

spring.flyway.enabled= true
spring.flyway.baseline-on-migrate= true
spring.flyway.locations= classpath: ****
spring.flyway.user= ****
spring.flyway.password= ****
spring.flyway.baseline-version= ****

---
jwt.secret.key= ******* 
```

- Run the Application

```
mvn spring-boot:run
```
## API Reference

#### Sign Up User

```http
  POST /api/auth/signup
```
##### Body (JSON)
```
{
  "firstname": "John",
  "lastname": "Doe",
  "username": "jondoe_2024",
  "password": "12345",
  "email": "john.doe@school.edu",
  "departmentId": 
}
```

#### Create Department

```http
POST api/department/create
```

##### Body (JSON)

````
{
    "name": "MATHS",
    "description" : "COMPUTER SCIENCE DEPARTMENT",
    "totalBudget" : ***
}
````

#### Create Expenditure

```http
POST api/expenditure/add
```

##### Body (JSON)

````
{
  "description": "Purchase of laboratory microscopes",
  "amount": 85000.00,
  "departmentId": ***,
  "categoryId": "***",
  "vendorId": "***",
  "requestedById": "***"
}
````


