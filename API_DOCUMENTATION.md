# Message Service API Documentation

## Overview

The Message Service provides a RESTful API for managing messages with consistent UTC timezone handling.

## Base URL

```
http://localhost:8080/api/messages
```

## Endpoints

### Create Message

**POST** `/api/messages`

Creates a new message with automatic UTC timestamp.

**Request Body:**
```json
{
  "sender": "John Doe",
  "content": "Hello, world!"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "sender": "John Doe",
  "content": "Hello, world!",
  "timestamp": "2025-07-31T19:30:00"
}
```

### Create Bulk Messages

**POST** `/api/messages/bulk`

Creates multiple messages with custom timestamps. Useful for importing historical data or creating messages with specific timestamps.

**Request Body:**
```json
{
  "messages": [
    {
      "sender": "Alice",
      "content": "First message",
      "timestamp": "2025-01-01T12:00:00"
    },
    {
      "sender": "Bob",
      "content": "Second message",
      "timestamp": "2025-01-02T15:30:00"
    }
  ]
}
```

**Response (201 Created):**
```json
[
  {
    "id": 1,
    "sender": "Alice",
    "content": "First message",
    "timestamp": "2025-01-01T12:00:00"
  },
  {
    "id": 2,
    "sender": "Bob",
    "content": "Second message",
    "timestamp": "2025-01-02T15:30:00"
  }
]
```

**Validation:**
- Maximum 100 messages per request
- Each message must have valid sender (1-100 characters) and content (1-1000 characters)
- Timestamps should be in ISO 8601 format

### Get All Messages (Last 365 Days)

**GET** `/api/messages`

Returns all messages from the last 365 days, ordered by timestamp ascending.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "sender": "John Doe",
    "content": "Hello, world!",
    "timestamp": "2025-07-31T19:30:00"
  }
]
```

### Get All Messages (Complete History)

**GET** `/api/messages/all`

Returns all messages in the database, ordered by timestamp descending.

### Get Message by ID

**GET** `/api/messages/{id}`

Returns a specific message by its ID.

**Response (200 OK):**
```json
{
  "id": 1,
  "sender": "John Doe",
  "content": "Hello, world!",
  "timestamp": "2025-07-31T19:30:00"
}
```

**Response (404 Not Found):**
If message with the specified ID doesn't exist.

### Get Messages by Sender

**GET** `/api/messages/sender/{sender}`

Returns all messages from a specific sender, ordered by timestamp descending.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "sender": "John Doe",
    "content": "Hello, world!",
    "timestamp": "2025-07-31T19:30:00"
  }
]
```

### Search Messages by Content

**GET** `/api/messages/search?keyword={keyword}`

Returns messages containing the specified keyword in their content.

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "sender": "John Doe",
    "content": "Hello, world!",
    "timestamp": "2025-07-31T19:30:00"
  }
]
```

### Get Messages by Time Range

**GET** `/api/messages/timerange?startTime={startTime}&endTime={endTime}`

Returns messages within the specified time range.

**Parameters:**
- `startTime`: ISO 8601 format (e.g., "2025-07-31T19:30:00")
- `endTime`: ISO 8601 format (e.g., "2025-07-31T20:30:00")

**Response (200 OK):**
```json
[
  {
    "id": 1,
    "sender": "John Doe",
    "content": "Hello, world!",
    "timestamp": "2025-07-31T19:30:00"
  }
]
```

### Delete Message

**DELETE** `/api/messages/{id}`

Deletes a message by its ID.

**Response (204 No Content):**
If message was successfully deleted.

**Response (404 Not Found):**
If message with the specified ID doesn't exist.

### Delete All Messages

**DELETE** `/api/messages`

Deletes all messages from the database. **Use with caution!**

**Response (200 OK):**
```json
{
  "message": "All messages deleted successfully",
  "deletedCount": 5
}
```

**Warning:** This operation is irreversible and will delete all messages in the database.

## Data Models

### CreateMessageRequest
```json
{
  "sender": "string (1-100 characters, required)",
  "content": "string (1-1000 characters, required)"
}
```

### CreateBulkMessageRequest
```json
{
  "messages": [
    {
      "sender": "string (1-100 characters, required)",
      "content": "string (1-1000 characters, required)",
      "timestamp": "string (ISO 8601 format, required)"
    }
  ]
}
```

### CreateBulkMessageItem
```json
{
  "sender": "string (1-100 characters, required)",
  "content": "string (1-1000 characters, required)",
  "timestamp": "string (ISO 8601 format, required)"
}
```

### MessageResponse
```json
{
  "id": "number (required)",
  "sender": "string (required)",
  "content": "string (required)",
  "timestamp": "string (ISO 8601 format, UTC timezone, required)"
}
```

## Timezone Information

- All timestamps are stored and returned in **UTC timezone**
- Timestamps are automatically generated when creating messages
- Time range queries should use UTC timestamps in ISO 8601 format

## Error Responses

### Validation Errors (400 Bad Request)
```json
{
  "timestamp": "2025-07-31T19:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/messages"
}
```

### Not Found (404)
```json
{
  "timestamp": "2025-07-31T19:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Message not found",
  "path": "/api/messages/999"
}
```

## Example Usage

### Create a message:
```bash
curl -X POST http://localhost:8080/api/messages \
  -H "Content-Type: application/json" \
  -d '{
    "sender": "Alice",
    "content": "Hello from Alice!"
  }'
```

### Create bulk messages with custom timestamps:
```bash
curl -X POST http://localhost:8080/api/messages/bulk \
  -H "Content-Type: application/json" \
  -d '{
    "messages": [
      {
        "sender": "Alice",
        "content": "Historical message from Alice",
        "timestamp": "2025-01-01T12:00:00"
      },
      {
        "sender": "Bob",
        "content": "Historical message from Bob",
        "timestamp": "2025-01-02T15:30:00"
      }
    ]
  }'
```

### Get all messages:
```bash
curl -X GET http://localhost:8080/api/messages
```

### Search messages:
```bash
curl -X GET "http://localhost:8080/api/messages/search?keyword=hello"
```

### Delete all messages:
```bash
curl -X DELETE http://localhost:8080/api/messages
``` 