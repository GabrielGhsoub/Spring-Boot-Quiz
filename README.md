# Spring Boot Sales System

This project implements a simple sales system using Spring Boot, designed to manage products, clients, and sales operations efficiently. It includes functionality for CRUD operations on products and clients, managing sales with multiple transactions, and logging updates on sale transactions.

## Features

- **Product Management**: Supports fetching, creating, and updating product details.
- **Client Management**: Enables fetching, creating, and editing client information.
- **Sales Operations**: Allows fetching all sales, creating new sales with multiple transactions, and editing sales details.
- **Logging**: Implements logging for all update operations on sale transactions.
- **Unit Testing**: Incorporates unit testing to ensure the reliability of the system.

## Running the Application

To run this application:

1. Clone the repository to your local machine.
2. Open your IDE (e.g., Eclipse, IntelliJ IDEA).
3. Import the project as a Maven project.
4. Locate the `SpringBootQuizApplication.java` file in the project explorer.
5. Run the application as a Spring Boot App.

## API Endpoints

Interact with the system through the following API endpoints:

### Products

- `GET /products`: Fetch all products.
- `POST /products`: Create a new product.
- `PUT /products/{id}`: Update an existing product.

### Clients

- `GET /clients`: Fetch all clients.
- `POST /clients`: Create a new client.
- `PUT /clients/{id}`: Edit an existing client.

### Sales

- `GET /sales`: Fetch all sales operations.
- `POST /sales`: Create new sales with multiple transactions.
- `PUT /sales/{saleId}/transactions`: Edit quantities and prices of the sale.

Refer to the provided Postman collection for detailed request and response structures.

## Unit Testing

Unit tests have been implemented across various components of the application to ensure functionality works as expected. Tests can be run directly within the IDE to validate the logic of services and controllers.

## Including the Class Diagram

The class diagram provided as an SVG file illustrates the structure and relationships within the application. It can be viewed directly in most modern browsers or any compatible image viewer.

## Further Documentation

For more detailed information on each component and service within the system, please refer to the JavaDoc comments provided within the source code.

