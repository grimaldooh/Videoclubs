# 🎬 VideoClubs Management System

A comprehensive video rental store management system built with Spring Boot, designed for complete business operations management.

## ✨ Features

- **📊 Inventory Management** - Complete catalog of movies with detailed information
- **👥 Customer Administration** - Customer registration, profiles, and rental history
- **🎯 Rental Operations** - Check-out/check-in system with due date tracking
- **📄 PDF Reports** - Automated report generation for business analytics
- **🔲 QR Code Integration** - Quick access and verification system
- **🔐 Security** - Spring Security implementation with role-based access
- **📱 Responsive Design** - Bootstrap-powered responsive web interface

## 🛠️ Technologies

- **Java** - Backend programming language
- **Spring Boot** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence layer
- **Hibernate** - ORM framework
- **Thymeleaf** - Server-side template engine
- **PostgreSQL** - Database management system
- **iTextPDF** - PDF generation library
- **Maven** - Dependency management
- **Bootstrap** - Frontend styling framework

## 🚀 Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- PostgreSQL 12+

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/grimaldooh/Videoclubs.git
cd Videoclubs
```

2. **Configure database**
```bash
# Create PostgreSQL database
createdb videoclubs_db

# Update application.properties with your database credentials
```

3. **Run the application**
```bash
mvn spring-boot:run
```

4. **Access the application**
```
http://localhost:8080
```

## 📋 Configuration

### Database Setup
```properties
# application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/videoclubs_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

## 🏗️ Architecture

```
src/
├── main/
│   ├── java/
│   │   └── com/videoclubs/
│   │       ├── controller/     # REST controllers
│   │       ├── service/        # Business logic
│   │       ├── repository/     # Data access layer
│   │       ├── model/          # Entity classes
│   │       └── config/         # Configuration classes
│   └── resources/
│       ├── templates/          # Thymeleaf templates
│       ├── static/            # CSS, JS, images
│       └── application.properties
```

## 📊 Key Features

### Inventory Management
- Add/edit/remove movies
- Category management
- Stock tracking
- Search and filtering

### Customer Management
- Customer registration
- Profile management
- Rental history tracking
- Membership status

### Rental Operations
- Movie check-out system
- Due date calculations
- Late fee management
- Return processing

### Reporting
- PDF rental reports
- Customer activity reports
- Inventory status reports
- Revenue analytics

## 🔐 Security Features

- **Authentication** - User login system
- **Authorization** - Role-based access control
- **Session Management** - Secure session handling
- **Data Validation** - Input sanitization and validation

## 📱 User Interface

- **Responsive Design** - Works on desktop, tablet, and mobile
- **Bootstrap Integration** - Modern and clean UI
- **Intuitive Navigation** - User-friendly interface
- **Real-time Updates** - Dynamic content updates

## 🧪 Testing

```bash
# Run unit tests
mvn test

# Run integration tests
mvn verify
```

## 📝 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Author

Angel Grimaldo - [GitHub](https://github.com/grimaldooh)

## 🤝 Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
