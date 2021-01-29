## Summary

TUI DX Backend technical Test

The base project uses lombok, so you have to install it. You can use the following guide https://www.baeldung.com/lombok-ide

### Technical solutions

**Server layers architecture**
* Controller
* Service
* Repository (to simplify used in memory implementation)

**security level** is based on spring-security and jwt authentication. to simplify used in memory user manager

**validation** is based on javax.validation specification and spring implementation

**event-driver** patter is implemented base on spring application events approach, events are handled asynchronously

### Require

* Java 11
* mvn
* docker

### Installation

#### clone
```bash
mvn git clone https://github.com/dkuhta/availability-api
```

#### build project
```bash
mvn clean install
```

#### build docker image
```bash
docker build -t availability-api .
``` 

#### run docker container
```bash
docker run -p 8080:8080 availability-api
```

#### application endpoint
```bash
http://localhost:8080
```

#### swagger ui endpoint
```bash
http://localhost:8080/swagger-ui.html
```

### Usage

#### Default user
```bash
spring.security.user.name=user
spring.security.user.password=password
```

#### Authentication
##### Request
```bash
POST: http://localhost:8080/auth
Content-type: "application/json"
Body: {"username": "user", "password": "password"}
```
##### Response
```bash
{
    "jwttoken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyIiwiaWF0IjoxNjExODMxNTg4LCJleHAiOjE2MTE5MTc5ODh9.nqgBHpSN9jzhZ08LrOyomI3KxzVxHYYzbQc8TqAW7vCaK0JomHDrFpYyowXBwp89gYoJfvJZ9k6QKH3ZAaJ4JQ"
}
```
##### Authorization header for secured endpoints
```bash
Authorization: "Bearer jwttoken"
```

#### Availability
The availability response has _"availability-id"_ header that must be used for booking.

```bash
Header "availability-id": "f1baed62-1233-4cb2-9715-8f5bb1255045"
```

Response is valid 15 minutes by default. Availability period can be modified with following property 
```bash
availability.expiration.minutes=15
```

#### Booking
Booking operation are async, status of operation can retrieved from events Rest API. API details can be found in swagger doc
