# Digital Library - Book Microservice

| Module          | Port | Purpose                                  |
|-----------------|------|------------------------------------------|
| eureka-server   | 8761 | Service registry (dashboard)             |
| api-gateway     | 9000 | Single entry point, routes /api/books/** |
| book-service    | 8081 | Book CRUD, PostgreSQL database `booksDB` |

Start order: 1) eureka-server  2) book-service  3) api-gateway
Edit the DB password in book-service/src/main/resources/application.properties
