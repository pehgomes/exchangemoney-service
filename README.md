# Exchange Money Service

exchangemoney-service is responsible for converting between currencies,
using updated conversion rates.

### Architecture Overview

### Technologies

* Java
* Spring Boot (Web, Data JPA, Actuator)
* H2
* Gradle
* Lombok
* Kotlin DSL

### Principles and Concepts

* Domain Driven Design
* SOLID
* Clean Code

### Installation

Use build automation system [gradlew](https://docs.gradle.org/current/userguide/userguide.html) to compile and run all test classes.


`Linux`
```bash
./gradlew clean build
```

`Windows`
```bash
gradlew clean build
```


### Running tests

`Linux`
```bash
./gradlew test
```

`Windows`
```bash
gradlew test
```

### Endpoints

| Method | URI              | Description              | Response Status            |
| ------ | ---------------- | ------------------------ | -------------------------- |
| `POST` | `/transactions` | *create new converting transaction*    | `201`, `422`, `500` |
| `GET` | `/transactions/customers/{taxId}` | *find transactions converted by customer*    | `200`, `404`, `500` |

#### POST `/transactions`

| Attribute | Mandatory              | Description              | Type            |
| ------ | ---------------- | ------------------------ | -------------------------- |
| `taxId` | `yes` | *taxId is like 'cpf' or a person's ID*    | `string` |
| `amount` | `yes` | *origin value to be converted*    | `numeric` |
| `from` | `yes` | *currency of origin of value*    | `string` |
| `to` | `yes` | *currency of destiny of value*    | `string` |

*Exanples below:*

`body`

```json
{
  "taxId": "05267832498",
  "amount": 10,
  "from":"BRL",
  "to":"USD"
}
```

`response`

```json
{
   "transactionId": "55f0c119-d4e7-4034-8f95-ebf00e318d5b",
   "customerId": "55f0c119-d4e7-4034-8f95-ebf00e318d5b",
   "from": "BRL",
   "origin": 10.0,
   "to": "USD",
   "destiny": 3.82,
   "rate": 0.19,
   "occurredOn": "2021-06-07T18:04:22.685898-03:00"
}
```

#### GET `/transactions/customers/05267832498`

`response`

```json
[
  {
   "transactionId": "55f0c119-d4e7-4034-8f95-ebf00e318d5b",
   "customerId": "55f0c119-d4e7-4034-8f95-ebf00e318d5b",
   "from": "BRL",
   "origin": 10.0,
   "to": "USD",
   "destiny": 3.82,
   "rate": 0.19,
   "occurredOn": "2021-06-07T18:04:22.685898-03:00"
  }, 
  {
    "transactionId": "342fb32f-5bc0-4082-8ca5-21c102b3dfa3",
    "customerId": "55f0c119-d4e7-4034-8f95-ebf00e318d5b",
    "from": "USD",
    "origin": 10.0,
    "to": "JPY",
    "destiny": 1096.65,
    "rate": 109.66,
    "occurredOn": "2021-06-08T22:04:22.685898-03:00"
  }
]
```