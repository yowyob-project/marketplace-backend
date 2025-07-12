# Marketplace Service

A Spring Boot microservice for managing an e-commerce marketplace platform.

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Docker and Docker Compose
- Git

## Technology Stack

- Spring Boot 3.x
- ScyllaDB (Cassandra compatible)
- Maven
- Docker

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/KingT1123/marketplace_synthesis_project.git
cd marketplace-service
```

### 2. Database Setup

Start ScyllaDB using Docker Compose:

```bash
docker-compose up -d
```

Verify ScyllaDB is running:
```bash
docker ps
```

You should see a container named `marketplace-scylla` running.

### 3. Build the Application

```bash
mvn clean install
```
OR
Go to your src/main/java/com/marketplace and run the file named "MarketplaceServiceApplication"

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints (Use Postman or any equivalent)

### Products (Ensure you create the table in the DB first)

- Create a product
  ```http
  POST /api/products
  ```
  ```json
  {
    "name": "Sample Product",
    "description": "Product description",
    "price": 99.99,
    "quantity": 10,
    "status": "AVAILABLE"
  }
  ```

- Get all products
  ```http
  GET /api/products
  ```

- Get product by ID
  ```http
  GET /api/products/{id}
  ```

- Update product
  ```http
  PUT /api/products/{id}
  ```

- Delete product
  ```http
  DELETE /api/products/{id}
  ```

## Database Schema 


The project uses ScyllaDB with the following table structure:
## - Create the Products table
1) In a new terminal, type the command
  ```bash
  docker exec -it marketplace-scylla cqlsh
  ```
2) Then use the database
   ```sql
     USE marketplace_db
   ```
3)  Then use the following command to create the table (it is also found in the schema.cql file in the resources folder) 
```sql
CREATE TABLE IF NOT EXISTS marketplace_db.products (
    id UUID PRIMARY KEY,
    name text,
    description text,
    seller_id UUID,
    price decimal,
    quantity int,
    status text,
    created_at timestamp,
    updated_at timestamp
);
```
4) Use the following command to see what is in the DB
  ```sql
    DESCRIBE TABLES;
  ```
Then,
  ```sql
    DESCRIBE TABLE products;
  ```

## Project Structure

```
src/main/java/com/marketplace/
├── config/          # Configuration classes
├── controllers/     # REST endpoints
├── services/        # Business logic
├── repositories/    # Data access
├── entities/        # Database entities
├── dtos/           # Data Transfer Objects
├── exceptions/      # Custom exceptions
└── utils/          # Utility classes
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
MIT