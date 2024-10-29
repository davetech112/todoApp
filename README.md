Overview
This To-do app is a straightforward task management application designed to help users efficiently create, update, and manage their daily tasks.

Features
User authentication with JWT (JSON Web Tokens).
Manage to-do items: Create, Read, Update, and Delete tasks.
User management: Sign up, sign in, and update profiles.
In-Memory Caching for faster task retrieval.
Mockito: Utilized for unit testing of services and controllers.
Rapid development facilitated by Spring Boot.
RESTful API architecture.
Swagger documentation for API endpoints.
Technologies Used
Spring Boot: Framework for building the application.
Spring Security with JWT: Provides token-based authentication for securing the app.
Spring Data JPA: Facilitates database interactions.
H2 Database: An in-memory database for fast and straightforward development.
In-Memory Cache: Caches frequently accessed data to enhance performance.
Mockito: Employed for unit testing.
Lombok: Reduces boilerplate code.
JUnit: Framework for writing and executing tests.
Running Tests
Run unit tests using Mockito and JUnit with the command:

bash
Copy code
mvn test
Cache Configuration
The application leverages an in-memory cache to store frequently accessed data, minimizing database queries and improving performance. Caching can be configured in the application.properties file or directly within the service layer using annotations.

Security
Authentication is implemented via JWT. Upon successful login, the client receives a token, which must be included with every request in the Authorization header as Bearer <token>.

API Documentation
API documentation is provided using the SpringFox library for Swagger configuration, detailing every endpoint along with their respective requests and responses.

Logging
Logging is handled using Spring SLF4J, with logs output to the console. Configuration options are available in the application.properties file.

Configuration
The application can be customized through the application.properties file, where you can set database connection details, caching configurations, and other application settings.
