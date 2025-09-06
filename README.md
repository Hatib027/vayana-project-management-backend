# Vayana Project Management Backend

A comprehensive REST API for project and task management built with Spring Boot.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
  - [Authentication](#authentication)
  - [Projects](#projects)
  - [Tasks](#tasks)
- [Additional Features](#additional-features)
- [Contributing](#contributing)

## Features

- 🔐 User authentication and authorization
- 📁 Project management (CRUD operations)
- ✅ Task management with status and priority tracking
- 🔍 Advanced search and filtering capabilities
- 📄 Pagination support for large datasets
- 📅 Date validation and management
- 🏷️ Task categorization with status and priority levels

## Tech Stack

- **Backend**: Spring Boot
- **Database**: MySQL
- **Authentication**: JWT (JSON Web Tokens)
- **Documentation**: OpenAPI/Swagger (if applicable)

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.2.4
- MYSQL 8.1

### Installation

1. Clone the repository
```bash
git clone https://github.com/your-username/vayana-project-management-backend.git
cd vayana-project-management-backend
```

2. Configure your database connection in `application.properties`

3. Run the application
```bash
mvn spring-boot:run
```

- Or

```
docker-compose up --build
```
- Swagger

```
http://localhost:8080/swagger-ui/index.html#
```
The API will be available at `http://localhost:8080`

## API Reference

### Authentication

#### 🔐 Register User
```http
POST /api/auth/register
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "Password@123",
  "name": "John Doe",
  "phone": "+919876543210"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "email": "user@example.com",
  "name": "John Doe",
  "phone": "+919876543210",
  "message": "User registered successfully"
}
```

#### 🔑 Login User
```http
POST /api/auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "Password@123"
}
```

**Response (202 Accepted):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Error Response (401 Unauthorized):**
```json
{
  "error": "Bad credentials"
}
```

### Projects

#### Create Project
```http
POST /api/project/add-project
```

**Request Body:**
```json
{
  "name": "Project Alpha",
  "description": "A new project description"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "name": "Project Alpha",
  "description": "A new project description",
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T10:30:00Z"
}
```

#### Get Project by ID
```http
GET /api/project/{id}
```

**Path Parameters:**
- `id` (required) - Project ID

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Project Alpha",
  "description": "A new project description",
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T10:30:00Z"
}
```

#### Update Project
```http
PUT /api/project/update-project/{id}
```

**Path Parameters:**
- `id` (required) - Project ID

**Request Body:**
```json
{
  "name": "Updated Project Name",
  "description": "Updated description"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "name": "Updated Project Name",
  "description": "Updated description",
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T11:45:00Z"
}
```

#### Search Projects
```http
GET /api/project/search-projects?name=Alpha&page=0&limit=10&sortBy=createdAt&order=desc
```

**Query Parameters:**
- `name` (optional) - Project name filter
- `page` (optional, default: 0) - Page number
- `limit` (optional, default: 10) - Items per page
- `sortBy` (optional, default: createdAt) - Sort field
- `order` (optional, default: desc) - Sort order (asc/desc)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Project Alpha",
      "description": "First project",
      "createdAt": "2023-10-15T10:30:00Z",
      "updatedAt": "2023-10-15T10:30:00Z"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "last": true
}
```

#### Delete Project
```http
DELETE /api/project/delete-project/{id}
```

**Path Parameters:**
- `id` (required) - Project ID

**Response (200 OK):**
```json
"Project deleted successfully"
```

### Tasks

#### Create Task
```http
POST /api/task/add-task
```

**Request Body:**
```json
{
  "title": "Implement user authentication",
  "description": "Add JWT-based authentication system",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2023-12-31",
  "projectId": 1
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "title": "Implement user authentication",
  "description": "Add JWT-based authentication system",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2023-12-31",
  "projectId": 1,
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T10:30:00Z"
}
```

#### Get Task by ID
```http
GET /api/task/{id}
```

**Path Parameters:**
- `id` (required) - Task ID

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Implement user authentication",
  "description": "Add JWT-based authentication system",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2023-12-31",
  "projectId": 1,
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T10:30:00Z"
}
```

#### Update Task
```http
PUT /api/task/update-task/{id}
```

**Path Parameters:**
- `id` (required) - Task ID

**Request Body:**
```json
{
  "title": "Updated task title",
  "description": "Updated task description",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "dueDate": "2023-12-25"
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "title": "Updated task title",
  "description": "Updated task description",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "dueDate": "2023-12-25",
  "projectId": 1,
  "createdAt": "2023-10-15T10:30:00Z",
  "updatedAt": "2023-10-15T12:00:00Z"
}
```

#### Delete Task
```http
DELETE /api/task/delete-task/{id}
```

**Path Parameters:**
- `id` (required) - Task ID

**Response (200 OK):**
```json
"Task deleted successfully"
```

#### Search Tasks
```http
POST /api/task/search-tasks
```

**Request Body:**
```json
{
  "projectIds": [1, 2, 3],
  "title": "authentication",
  "status": "TODO",
  "priority": "HIGH",
  "dueDate": "2023-12-31",
  "page": 0,
  "size": 10,
  "sortBy": "createdAt",
  "orderBy": "desc"
}
```

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "title": "Implement user authentication",
      "description": "Add JWT-based authentication system",
      "status": "TODO",
      "priority": "HIGH",
      "dueDate": "2023-12-31",
      "projectId": 1,
      "createdAt": "2023-10-15T10:30:00Z",
      "updatedAt": "2023-10-15T10:30:00Z"
    }
  ],
  "page": 0,
  "size": 10,
  "totalElements": 1,
  "totalPages": 1,
  "last": true
}
```




```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.

## Support

For support and questions, please contact [your-email@example.com](mailto:your-email@example.com) or create an issue in the repository.
