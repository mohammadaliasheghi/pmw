# Auth Guard Application

A secure and robust Auth Guard application built with Spring Boot, featuring JWT-based authentication and PostgreSQL database integration. This application allows users to securely store, manage, and access their passwords with enterprise-grade security.

## Features

- 🔐 **Secure Authentication**: JWT-based login and authentication system
- 🗝️ **Password Management**: Store, retrieve, update, and delete passwords
- 🛡️ **Encryption**: Strong encryption for stored passwords
- 📱 **RESTful API**: Well-designed REST APIs for all operations
- 💾 **PostgreSQL Database**: Reliable data persistence
- 🧪 **H2 Console**: Database console available for development (can be disabled in production)

## Technology Stack

- **Framework**: Spring Boot
- **Authentication**: JWT (JSON Web Tokens)
- **Database**: PostgreSQL with Hibernate JPA
- **Build Tool**: Maven
- **Security**: Custom password encryption with HmacSHA256
- **Configuration**: YAML-based configuration

## Prerequisites

- Java 11 or higher
- Maven 3.6+
- PostgreSQL 12+
- Git

## Installation & Setup

### 1. Clone the Repository
```bash
git clone https://github.com/yourusername/AuthGuard.git
cd AuthGuard
```

### 2. Configure Database
Create a PostgreSQL database:
```sql
CREATE DATABASE platform;
```

### 3. Update Database Configuration
The application is configured to connect to PostgreSQL with the following default settings:
- **URL**: `jdbc:postgresql://localhost:5432/platform`
- **Username**: `postgres`
- **Password**: `postgres`

Modify these in `application.yml` if your PostgreSQL setup differs.

### 4. Build the Application
```bash
mvn clean install
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:7881`

## Configuration Details

### Application Properties

```yaml
# Server Configuration
server.port: 7881

# Database Configuration
spring.datasource:
  url: jdbc:postgresql://localhost:5432/platform
  username: postgres
  password: postgres

# JPA Configuration
spring.jpa:
  hibernate.ddl-auto: update
  show-sql: true
  properties.hibernate.dialect: org.hibernate.dialect.PostgreSQLDialect

# H2 Console (Development Only)
spring.h2.console.enabled: true
```

### Security Configuration

The application uses JWT for authentication with the following security settings:

```yaml
security:
  password:
    secret-key: ENC(tZmy22OyWpLFjfXkcQs/uHnjgtXBx5JMHjCkMRMZZFXAk9m9W6IG8Jdl4oCCUfe73fnmuv5FH3WP77368VYpZQ==)
    encode-key: ENC(Rp02msNwKCOvaVMGM1h6pIO0UK6ce0aDbIfbjI7otBKAATYLJnOqaVLd7RHBYw6Ivr52TkgCCS/RJRKyjP3B6Q==)
  algorithm:
    encode-alg: HmacSHA256
  token:
    expiration-time: 600000  # 10 minutes in milliseconds
```

## Authentication Flow

1. **User Registration**: Users can create an account with their credentials
2. **Login**: Users authenticate with their username/email and password
3. **JWT Generation**: Upon successful authentication, a JWT token is generated
4. **Token Usage**: The JWT token must be included in subsequent API requests
5. **Token Expiry**: Tokens expire after 10 minutes (configurable)

### API Authentication Header
```
Authorization: Bearer <your-jwt-token>
```

## Development Features

### H2 Database Console
For development purposes, the H2 database console is enabled. Access it at:
```
http://localhost:7881/h2-console
```

### SQL Error Handling
The application continues on SQL initialization errors, making development more forgiving:
```yaml
spring.sql.init.continue-on-error: true
```

### Detailed Error Messages
Server errors include detailed messages for easier debugging:
```yaml
server.error.include-message: always
```

## Security Considerations

⚠️ **Production Deployment Notes:**

1. **Disable H2 Console** in production:
   ```yaml
   spring.h2.console.enabled: false
   ```

2. **Use Environment Variables** for sensitive data:
   ```yaml
   spring.datasource.password: ${DB_PASSWORD}
   security.password.secret-key: ${JWT_SECRET_KEY}
   ```

3. **Adjust Token Expiration** based on your security requirements

4. **Enable HTTPS** in production

5. **Use Stronger Password Encryption** for production environments

## Building for Production

Create a production-ready JAR file:
```bash
mvn clean package
```

Run the production JAR:
```bash
java -jar target/auth-guard-*.jar
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## Acknowledgments

- Spring Boot team for the excellent framework
- JWT for secure authentication
- All contributors and users of this project

---

**Note**: This application is for educational and personal use. Always follow security best practices when deploying Auth Guard systems in production environments.