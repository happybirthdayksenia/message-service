# Message Service

A Kotlin Spring Boot microservice for storing and retrieving messages with PostgreSQL database.

## Features

- Store messages with sender, content, and timestamp
- Retrieve messages by ID, sender, or time range
- Search messages by content
- RESTful API with proper error handling
- PostgreSQL database integration
- Input validation

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher

## Setup

1. **Create PostgreSQL Database**
   ```sql
   CREATE DATABASE message_db;
   ```

2. **Update Database Configuration**
   Edit `src/main/resources/application.yml` and update the database connection details:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/message_db
       username: your_username
       password: your_password
   ```

3. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## API Endpoints

### Create Message
- **POST** `/api/messages`
- **Body:**
  ```json
  {
    "sender": "John Doe",
    "content": "Hello, this is a test message!"
  }
  ```
- **Response:** 201 Created with message details

### Get All Messages
- **GET** `/api/messages`
- **Response:** List of latest 10 messages

### Get Message by ID
- **GET** `/api/messages/{id}`
- **Response:** Message details or 404 if not found

### Get Messages by Sender
- **GET** `/api/messages/sender/{sender}`
- **Response:** List of messages from the specified sender

### Search Messages by Content
- **GET** `/api/messages/search?keyword=hello`
- **Response:** List of messages containing the keyword

### Get Messages by Time Range
- **GET** `/api/messages/timerange?startTime=2024-01-01T00:00:00&endTime=2024-01-31T23:59:59`
- **Response:** List of messages within the specified time range

### Delete Message
- **DELETE** `/api/messages/{id}`
- **Response:** 204 No Content if deleted, 404 if not found

## Example Usage

### Create a Message
```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "sender": "Alice",
    "content": "Hello from Alice!"
  }'
```

### Get All Messages
```bash
curl http://localhost:8080/api/messages
```

### Get Messages by Sender
```bash
curl http://localhost:8080/api/messages/sender/Alice
```

### Search Messages
```bash
curl "http://localhost:8080/api/messages/search?keyword=hello"
```

## Database Schema

The application automatically creates the following table:

```sql
CREATE TABLE messages (
    id BIGSERIAL PRIMARY KEY,
    sender VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL
);
```

## Error Handling

The API returns appropriate HTTP status codes and error messages:

- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `500 Internal Server Error` - Server errors

Error response format:
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "sender: Sender is required"
}
```

## Development

### Running Tests
```bash
mvn test
```

### Building JAR
```bash
mvn clean package
```

The executable JAR will be created in the `target` directory.

## Configuration

Key configuration options in `application.yml`:

- `server.port` - Application port (default: 8080)
- `spring.jpa.hibernate.ddl-auto` - Database schema generation (default: update)
- `spring.jpa.show-sql` - Show SQL queries in logs (default: true) 