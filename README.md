# 📚 BookNest - Professional Book Management System

A modern Spring Boot application for managing books and reviews with JWT authentication, role-based authorization, and complete CRUD operations.

## 🚀 Features

- **🔐 JWT Authentication** - Secure token-based authentication
- **🛡️ Role-Based Authorization** - USER, AUTHOR, and ADMIN roles
- **🔒 Password Security** - BCrypt password hashing
- **📖 Book Management** - Complete CRUD operations for books
- **⭐ Review System** - User reviews with 1-5 star ratings
- **🎯 Professional APIs** - RESTful endpoints with proper error handling
- **🗄️ PostgreSQL Integration** - Robust database management

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.5.3, Spring Security, Spring Data JPA
- **Database**: PostgreSQL
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: BCrypt Password Encoding
- **Build Tool**: Maven
- **Java Version**: 21

## 📋 Prerequisites

Before running the application, ensure you have:

- Java 21 or higher
- Maven 3.6 or higher
- PostgreSQL 12 or higher
- Git

## 🔧 Installation & Setup

### 1. Clone the Repository
```bash
git clone <your-repository-url>
cd webBook
```

### 2. Database Setup
Create a PostgreSQL database:
```sql
CREATE DATABASE Book;
CREATE USER root WITH PASSWORD 'root';
GRANT ALL PRIVILEGES ON DATABASE Book TO root;
```

### 3. Configure Database Connection
Update `src/main/resources/application.properties` if needed:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Book
spring.datasource.username=root
spring.datasource.password=root
```

### 4. Install Dependencies
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 👥 User Roles

| Role | Permissions |
|------|-------------|
| **USER** | Create reviews, view books and reviews |
| **AUTHOR** | All USER permissions + create/update/delete own books |
| **ADMIN** | Full access to all operations |

## 🔑 Authentication

### Register a New User
```bash
curl -X POST http://localhost:8080/api/users/create \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "email": "john@example.com", 
    "password": "securePassword123",
    "role": "USER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "securePassword123"
  }'
```

**Response:**
```json
{
  "message": "User logged in successfully",
  "statusCode": 200,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "username": "john_doe",
    "email": "john@example.com",
    "role": "USER",
    "userId": 1
  }
}
```

## 📚 API Documentation

### Authentication Headers
For protected endpoints, include the JWT token:
```
Authorization: Bearer <your-jwt-token>
```

### 📖 Books API

#### Create Book (ADMIN/AUTHOR only)
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "The Great Gatsby",
    "author": "F. Scott Fitzgerald",
    "genre": "Classic Literature",
    "description": "A classic American novel",
    "coverImageUrl": "https://example.com/cover.jpg"
  }'
```

#### Get All Books (Public)
```bash
curl -X GET http://localhost:8080/api/books
```

#### Get Book by ID (Public)
```bash
curl -X GET http://localhost:8080/api/books/1
```

#### Update Book (Owner/ADMIN only)
```bash
curl -X PUT http://localhost:8080/api/books/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Title",
    "author": "Updated Author",
    "genre": "Updated Genre",
    "description": "Updated description"
  }'
```

#### Delete Book (Owner/ADMIN only)
```bash
curl -X DELETE http://localhost:8080/api/books/1 \
  -H "Authorization: Bearer <token>"
```

#### Get My Books (AUTHOR/ADMIN only)
```bash
curl -X GET http://localhost:8080/api/books/my-books \
  -H "Authorization: Bearer <token>"
```

### ⭐ Reviews API

#### Create Review (Authenticated Users)
```bash
curl -X POST http://localhost:8080/api/reviews \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "rating": 5,
    "comment": "Excellent book! Highly recommended.",
    "book": {"id": 1}
  }'
```

#### Get All Reviews (Public)
```bash
curl -X GET http://localhost:8080/api/reviews
```

#### Get Reviews by Book (Public)
```bash
curl -X GET http://localhost:8080/api/reviews/book/1
```

#### Get My Reviews (Authenticated Users)
```bash
curl -X GET http://localhost:8080/api/reviews/my-reviews \
  -H "Authorization: Bearer <token>"
```

#### Delete Review (Owner/ADMIN only)
```bash
curl -X DELETE http://localhost:8080/api/reviews/1 \
  -H "Authorization: Bearer <token>"
```

### 👥 Users API

#### Get All Users (ADMIN only)
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer <token>"
```

## 🔒 Security Features

- **Password Hashing**: All passwords are hashed using BCrypt
- **JWT Tokens**: Secure token-based authentication with 24-hour expiration
- **Role-Based Access**: Fine-grained permissions based on user roles
- **CORS Support**: Configured for cross-origin requests
- **Input Validation**: Comprehensive validation for all inputs

## 📊 Database Schema

### Users Table
- `id` (Primary Key)
- `username` (Unique)
- `email` (Unique)
- `password` (BCrypt hashed)
- `role` (USER/AUTHOR/ADMIN)
- `created_at`, `updated_at`

### Books Table
- `id` (Primary Key)
- `title`, `author`, `genre`
- `description`, `cover_image_url`
- `created_by` (Foreign Key to Users)
- `created_at`

### Reviews Table
- `id` (Primary Key)
- `rating` (1-5)
- `comment`
- `user_id` (Foreign Key to Users)
- `book_id` (Foreign Key to Books)
- `created_at`

## ✅ Business Rules

### Books
- Only ADMIN and AUTHOR users can create books
- Authors can only modify their own books
- ADMIN users can modify any book
- All users can view books (public access)

### Reviews
- Users can only review each book once
- Rating must be between 1-5
- Users can only delete their own reviews
- ADMIN can delete any review
- All reviews are publicly viewable

## 🚦 Error Handling

The API returns consistent error responses:

```json
{
  "message": "Error description",
  "statusCode": 400,
  "data": null
}
```

### Common HTTP Status Codes
- `200` - Success
- `201` - Created
- `400` - Bad Request
- `401` - Unauthorized
- `403` - Forbidden
- `404` - Not Found
- `409` - Conflict

## 🧪 Testing the API

### Complete User Flow Example

1. **Create an AUTHOR user:**
```bash
curl -X POST http://localhost:8080/api/users/create \
  -H "Content-Type: application/json" \
  -d '{"username":"author1","email":"author@example.com","password":"password123","role":"AUTHOR"}'
```

2. **Login to get token:**
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"author1","password":"password123"}'
```

3. **Create a book:**
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"My Book","author":"Author Name","genre":"Fiction","description":"A great book"}'
```

4. **Create a USER and review the book:**
```bash
# Create user
curl -X POST http://localhost:8080/api/users/create \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","email":"user@example.com","password":"password123","role":"USER"}'

# Login as user
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"password123"}'

# Create review
curl -X POST http://localhost:8080/api/reviews \
  -H "Authorization: Bearer USER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"rating":5,"comment":"Amazing book!","book":{"id":1}}'
```

## 🐳 Docker Support

If you have `docker-compose.yml` in your project, you can run:

```bash
docker-compose up -d
```

## 📝 Configuration

### JWT Configuration (application.properties)
```properties
jwt.secret=BookNestSecretKeyForJWTTokenGenerationAndValidation123456789
jwt.expiration=86400000
```

### Database Configuration
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/Book
spring.datasource.username=root
spring.datasource.password=root
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🆘 Support

If you encounter any issues or have questions:

1. Check the application logs for error details
2. Ensure your PostgreSQL database is running
3. Verify your JWT tokens are valid and not expired
4. Check that you have the correct permissions for the operation

---

**Happy Coding! 🚀** 