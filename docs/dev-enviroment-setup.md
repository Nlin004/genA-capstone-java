# Development Environment Setup

## Overview

This guide walks you through setting up your local development environment for the Library Management System API. You'll
learn how to start the PostgreSQL database using Docker, configure your Spring Boot application, and verify everything
is working correctly.

The provided starter project includes pre-configured files for database connectivity and deployment, so you can focus on
building the core functionality of your library system.

---

## Project Structure

```
api/
├── .idea/                              # IntelliJ IDEA configuration
├── .mvn/                               # Maven wrapper files
├── docs/                               # Project documentation
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.library/
│   │   │       ├── controllers/        # REST controllers (you will create)
│   │   │       ├── ApiApplication.java # Spring Boot main class
│   │   │       ├── config/            # Configuration classes (you will create)
│   │   │       ├── entity/            # JPA entities (you will create)
│   │   │       ├── repository/        # Spring Data repositories (you will create)
│   │   │       ├── service/           # Business logic (you will create)
│   │   │       ├── dto/               # Data transfer objects (you will create)
│   │   │       └── security/          # Security configuration (you will create)
│   │   └── resources/
│   │       ├── static/                # Static files
│   │       ├── templates/             # Templates (if needed)
│   │       ├── application.properties      # Local config (PROVIDED)
│   │       └── application-prod.properties # AWS config (PROVIDED)
│   └── test/
│       └── java/                      # Test classes (you will create)
├── target/                            # Compiled files (generated)
├── .gitattributes                     # Git attributes
├── .gitignore                         # Git ignore rules
├── docker-compose.yml                 # PostgreSQL setup (PROVIDED)
├── mvnw                              # Maven wrapper (Unix)
├── mvnw.cmd                          # Maven wrapper (Windows)
├── pom.xml                           # Maven dependencies
└── README.md                         # This file
```

---

## Provided Configuration Files

You've been given three essential configuration files that handle database connectivity and application settings.
Understanding these files will help you work effectively in both local development and production environments.

### 1. docker-compose.yml

This Docker Compose file sets up a PostgreSQL database container for local development. Docker Compose allows you to
define and run your database in an isolated container, ensuring consistency across different development machines.

**What it provides:**

- PostgreSQL 15 database server running in a container
- Pre-configured database named `librarydb`
- Default credentials (username: `postgres`, password: `postgres`)
- Port mapping to access the database on `localhost:5432`
- Persistent data storage using Docker volumes

**Why Docker?** Using Docker for your database means you don't need to install PostgreSQL directly on your machine. The
database runs in an isolated environment, and you can easily start, stop, or reset it without affecting your system.

### 2. application.properties (Local Development)

This file contains all configuration settings for running the application on your local machine. Spring Boot
automatically reads this file when the application starts, using these settings to connect to your Docker PostgreSQL
database.

**Key Configuration Sections:**

**Database Connection:**

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=postgres
spring.datasource.password=postgres
```

These settings tell Spring Boot how to connect to the PostgreSQL container running on your machine. The URL
specifies `localhost:5432` because Docker exposes the PostgreSQL port to your local network.

**JPA/Hibernate Settings:**

```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

The `ddl-auto=update` setting is crucial for development. Hibernate will automatically create or update database tables
based on your entity classes. When you create a `User` entity with JPA annotations and run the application, Hibernate
generates the corresponding SQL to create the `users` table. The `show-sql=true` setting displays all generated SQL
queries in your console, helping you understand what's happening behind the scenes.

**JWT Configuration:**

```properties
jwt.secret=local-dev-secret-key-min-256-bits-change-in-production
jwt.expiration=86400
```

JWT (JSON Web Token) is used for user authentication. The secret key signs and verifies tokens, while the expiration is
set to 86400 seconds (24 hours). In local development, the secret is hardcoded for convenience, but in production, it
must be provided via environment variables.

**Server and Logging:**

```properties
server.port=8080
logging.level.com.library=DEBUG
```

The application runs on port 8080, and debug-level logging is enabled for your library package, providing detailed
information during development.

### 3. application-prod.properties (AWS Production)

This file is specifically designed for deploying your application to AWS Elastic Beanstalk with an RDS PostgreSQL
database. Unlike the local configuration, it uses environment variables instead of hardcoded values for security.

**Key Differences from Local Configuration:**

**Environment Variables for Database:**

```properties
spring.datasource.url=jdbc:postgresql://${RDS_HOSTNAME}:${RDS_PORT}/${RDS_DB_NAME}
spring.datasource.username=${RDS_USERNAME}
spring.datasource.password=${RDS_PASSWORD}
```

AWS Elastic Beanstalk injects these environment variables at runtime. You'll configure them through the AWS Console when
deploying (Milestone 6). This approach keeps sensitive credentials out of your codebase.

**Production-Optimized Settings:**

```properties
spring.jpa.show-sql=false
server.port=5000
logging.level.root=WARN
```

SQL logging is disabled to improve performance, the server runs on port 5000 (Elastic Beanstalk requirement), and
logging is set to WARN level to reduce noise in production logs.

**Required AWS Environment Variables:**

- `RDS_HOSTNAME` - Your RDS database endpoint (e.g., `library-db.xxxxx.us-east-1.rds.amazonaws.com`)
- `RDS_PORT` - Database port (default: `5432`)
- `RDS_DB_NAME` - Database name (e.g., `librarydb`)
- `RDS_USERNAME` - Database username
- `RDS_PASSWORD` - Database password
- `JWT_SECRET` - Production JWT secret (generate with `openssl rand -base64 32`)

---

## Getting Started

### Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java 17 or 21**: The JDK (Java Development Kit) is required to compile and run Spring Boot applications. Check your
  version with `java -version`.
- **Maven**: Build tool for managing dependencies and building the project. The Maven wrapper (`mvnw`) is included in
  the project, so a separate Maven installation is optional.
- **Docker Desktop**: Required to run the PostgreSQL database container. Download
  from [docker.com](https://www.docker.com/products/docker-desktop).
- **IDE**: IntelliJ IDEA (recommended), Eclipse, or Visual Studio Code with Java extensions.

### Step 1: Start PostgreSQL Database

Docker Compose makes it simple to start your database with a single command. This creates and starts a PostgreSQL
container that persists data even after you stop it.

```bash
# Navigate to your project directory
cd api

# Start PostgreSQL container in detached mode (runs in background)
docker-compose up -d

# Verify the container is running
docker ps
```

You should see output showing `library-postgres` running on port 5432. The `-d` flag runs the container in the
background, so your terminal remains available.

**Accessing the Database:**

Your PostgreSQL database is now accessible at:

- **Host**: `localhost`
- **Port**: `5432`
- **Database**: `librarydb`
- **Username**: `postgres`
- **Password**: `postgres`

You can connect using any PostgreSQL client (pgAdmin, DBeaver, DataGrip) with these credentials to inspect tables and
data.

**Managing the Database Container:**

```bash
# Stop the container (data persists)
docker-compose down

# Stop and remove all data (WARNING: deletes everything)
docker-compose down -v

# View container logs
docker logs library-postgres

# Restart the container
docker-compose restart
```

### Step 2: Build the Project

Maven compiles your Java code, downloads dependencies, and packages everything into an executable JAR file. The first
build may take a few minutes as Maven downloads all required libraries.

```bash
# Clean any previous builds and compile
./mvnw clean install

# On Windows, use:
mvnw.cmd clean install
```

The `clean` command removes old compiled files, and `install` compiles your code, runs tests, and installs the artifact
in your local Maven repository.

### Step 3: Run the Application

Spring Boot's Maven plugin makes it easy to run your application directly from source code without manually creating JAR
files.

```bash
# Run the application
./mvnw spring-boot:run

# On Windows:
mvnw.cmd spring-boot:run
```

Watch the console output as Spring Boot starts. You'll see:

1. Spring Boot banner
2. Database connection pool initialization (HikariCP)
3. Hibernate generating DDL statements (creating tables)
4. Application startup completion with timing information

**The application will be accessible at:** `http://localhost:8080`

### Step 4: Verify Everything Works

Once the application starts, verify it's functioning correctly:

```bash
# Check the health endpoint
curl http://localhost:8080/actuator/health

# Expected response:
{"status":"UP"}
```

The health endpoint confirms the application is running and can connect to the database. If the database connection
fails, the status will show "DOWN" with error details.

**What to Look For in Logs:**

```
✓ HikariPool-1 - Start completed.
✓ Hibernate: create table users (...)
✓ Hibernate: create table books (...)
✓ Hibernate: create table reservations (...)
✓ Started ApiApplication in 3.456 seconds
```

These log messages confirm successful database connectivity and table creation. If you don't see table creation logs,
check that your entities have proper `@Entity` annotations.

---

## Database Schema Management

One of Spring Boot's powerful features is automatic schema management through Hibernate. You don't need to write SQL
scripts manually—Hibernate generates the necessary DDL (Data Definition Language) statements based on your Java entity
classes.

### How Automatic Schema Generation Works

When you create entity classes with JPA annotations and run your application:

1. **Hibernate scans** for classes annotated with `@Entity`
2. **Analyzes** the field types, annotations, and relationships
3. **Generates SQL** CREATE TABLE statements
4. **Executes** these statements against your PostgreSQL database

For example, when you create this User entity:

```java

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;

    @Column(unique = true, nullable = false)
    private String email;

    // ... other fields
}
```

Hibernate automatically generates and executes:

```sql
CREATE TABLE users
(
    user_id UUID PRIMARY KEY,
    email   VARCHAR(255) UNIQUE NOT NULL, .
    .
    .
);
```

### Understanding ddl-auto Modes

The `spring.jpa.hibernate.ddl-auto` property controls Hibernate's schema generation behavior:

- **`update`** (Used in this project): Creates tables if they don't exist and alters existing tables to match entity
  changes. This is safe for development because it preserves existing data while adding new columns or tables.

- **`create`**: Drops all tables and recreates them every time the application starts. **Destructive—all data is lost!**
  Useful for testing with fresh data.

- **`create-drop`**: Creates tables on startup and drops them on shutdown. Typically used for integration tests.

- **`validate`**: Only validates that the database schema matches your entities. Doesn't create or modify tables. Used
  in production with proper migration tools.

- **`none`**: Hibernate does nothing. You manage the schema manually or with migration tools.

**We use `update`** because it balances convenience with safety during development and works well for AWS deployment.

### Creating Seed Data

After Hibernate creates your tables, you'll need sample data for testing. Here are three approaches:

**Option 1: CommandLineRunner Component (Recommended)**

Create a component that runs once when the application starts:

```java

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) {
        // Only seed if database is empty
        if (bookRepository.count() == 0) {
            Book book1 = new Book();
            book1.setIsbn("978-0-13-468599-1");
            book1.setTitle("Clean Code");
            book1.setAuthor("Robert C. Martin");
            book1.setGenre("Technology");
            book1.setTotalCopies(5);
            book1.setAvailableCopies(5);
            bookRepository.save(book1);

            // Add more seed data...
        }
    }
}
```

This approach is type-safe and integrates with your Java code. The `count()` check prevents duplicate data on restarts.

---

## Development Workflow

### Daily Development Process

Here's the typical workflow when working on this project:

**Morning Startup:**

```bash
# 1. Start the database
docker-compose up -d

# 2. Run the application
./mvnw spring-boot:run

# 3. Application is ready at http://localhost:8080
```

**During Development:**

- Make code changes in your IDE
- Save files (Spring Boot DevTools can auto-reload, if configured)
- Restart the application to see changes: Stop with `Ctrl+C`, run `./mvnw spring-boot:run` again
- Test endpoints using Postman or curl
- Check logs for errors or SQL queries

**End of Day:**

```bash
# Stop the application: Ctrl+C

# Stop the database (data persists)
docker-compose down
```

### Local Development Details

**Environment:**

- **Profile**: `dev` (default from `spring.profiles.active`)
- **Database**: Docker PostgreSQL on `localhost:5432`
- **Application Port**: `8080`
- **Schema Management**: Hibernate DDL auto-update
- **Logging**: Verbose (DEBUG level for your code)

**What Happens When You Run Locally:**

1. Spring Boot reads `application.properties`
2. Connects to Docker PostgreSQL at `localhost:5432`
3. Hibernate scans entity classes
4. Creates or updates tables as needed
5. Runs DataLoader (if you created one)
6. Application starts and listens on port 8080
7. Health endpoint becomes available
8. You can now test your endpoints

### AWS Production Deployment (Milestone 6)

When you're ready to deploy to AWS:

```bash
# 1. Build production JAR
./mvnw clean package -DskipTests

# 2. Locate the JAR file
ls target/api-0.0.1-SNAPSHOT.jar

# 3. Upload to AWS Elastic Beanstalk (detailed in Milestone 6)

# 4. Configure AWS environment variables through console

# 5. AWS automatically activates production profile
```

**Production Environment:**

- **Profile**: `prod` (set by AWS environment variable)
- **Database**: AWS RDS PostgreSQL
- **Application Port**: `5000` (Elastic Beanstalk requirement)
- **Schema Management**: Hibernate DDL auto-update (creates tables on RDS)
- **Logging**: Minimal (WARN level)

The key difference is that production reads `application-prod.properties` and expects all database credentials and JWT
secrets through environment variables rather than hardcoded values.

---

## Understanding Configuration

### Why Two Configuration Files?

Separating local and production configurations follows the "Twelve-Factor App" methodology, which recommends storing
configuration in environment variables. This separation provides several benefits:

**Development (`application.properties`):**

- **Convenience**: Hardcoded values work without extra setup
- **Debugging**: Verbose logging and SQL output help identify issues
- **Local Database**: Uses Docker PostgreSQL on your machine
- **Security**: Less critical since it's not exposed to the internet

**Production (`application-prod.properties`):**

- **Security**: Sensitive values come from environment variables, never committed to Git
- **Performance**: Reduced logging improves response times
- **Scalability**: AWS RDS handles database connection pooling and backups
- **Different Port**: Port 5000 aligns with Elastic Beanstalk expectations

### Profile Selection Mechanism

Spring Boot's profile system allows you to activate different configurations:

```bash
# Default: uses application.properties
./mvnw spring-boot:run

# Explicitly set production profile (for testing prod config locally)
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Or via environment variable
export SPRING_PROFILES_ACTIVE=prod
./mvnw spring-boot:run
```

AWS Elastic Beanstalk automatically sets `SPRING_PROFILES_ACTIVE=prod` through its environment configuration, so your
application knows to use production settings when deployed.

### Configuration Comparison Table

| Setting                   | Local Development     | AWS Production         |
|---------------------------|-----------------------|------------------------|
| **Database**              | Docker PostgreSQL     | AWS RDS PostgreSQL     |
| **Host**                  | localhost:5432        | RDS endpoint           |
| **Schema Mode**           | update (auto-create)  | update (auto-create)   |
| **SQL Logging**           | Enabled (see queries) | Disabled (performance) |
| **Application Log Level** | DEBUG (verbose)       | WARN (errors only)     |
| **Server Port**           | 8080                  | 5000                   |
| **JWT Secret**            | Hardcoded string      | Environment variable   |
| **Credentials**           | Hardcoded             | Environment variables  |

This table highlights how the same application behaves differently based on which profile is active, optimizing for
either development speed or production efficiency.

---

## Common Issues and Solutions

### Issue: Port 5432 Already in Use

**Symptoms**: Docker Compose fails to start with an error like "port is already allocated"

**Cause**: You likely have PostgreSQL installed directly on your machine, and it's already using port 5432.

**Solutions**:

```bash
# Option 1: Stop local PostgreSQL service
sudo service postgresql stop

# Option 2: Change Docker's port mapping
# Edit docker-compose.yml, change:
ports:
  - "5433:5432"  # Use 5433 on host, 5432 in container

# Then update application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5433/librarydb
```

### Issue: Docker Container Won't Start

**Symptoms**: `docker-compose up` fails or container exits immediately

**Cause**: Docker Desktop isn't running or hasn't finished initializing

**Solution**:

1. Launch Docker Desktop application
2. Wait for the Docker icon in your system tray to stabilize (not spinning)
3. Run `docker ps` to verify Docker is responsive
4. Try `docker-compose up -d` again

### Issue: Application Can't Connect to Database

**Symptoms**: Error message like "Connection refused" or "Could not create connection to database server"

**Diagnosis**:

```bash
# Check if PostgreSQL container is running
docker ps

# Should show library-postgres with status "Up"

# Check container logs for errors
docker logs library-postgres

# Test connection manually
psql -h localhost -p 5432 -U postgres -d librarydb
# Password: postgres
```

**Solutions**:

- Ensure `docker-compose up -d` completed successfully
- Verify `application.properties` has correct host (`localhost`) and port (`5432`)
- Check firewall isn't blocking port 5432
- Restart Docker Desktop if containers aren't responding

### Issue: Tables Aren't Created

**Symptoms**: Application starts but database tables are missing

**Cause**: Hibernate didn't generate DDL statements

**Diagnosis**:

```bash
# Check logs for Hibernate DDL statements
# Should see: "Hibernate: create table users (...)"

# Connect to database and list tables
docker exec -it library-postgres psql -U postgres -d librarydb
\dt
```

**Solutions**:

1. Verify entities have `@Entity` annotation
2. Check `application.properties` has `spring.jpa.hibernate.ddl-auto=update`
3. Ensure entities are in the correct package (Spring Boot scans from main class package downward)
4. Look for startup errors in logs indicating mapping problems
5. Try `ddl-auto=create` temporarily to force table recreation (WARNING: deletes data)

### Issue: Need to Reset Database

**Symptoms**: Database is in an inconsistent state, bad test data, or schema changes aren't applying

**Solution**:

```bash
# Nuclear option: destroy and recreate everything
docker-compose down -v  # -v removes volumes (data)
docker-compose up -d
./mvnw spring-boot:run

# Hibernate will create fresh tables
# DataLoader will seed fresh data
```

This gives you a completely clean slate, useful when testing schema changes or after making mistakes with seed data.

### Issue: Application Won't Start

**Symptoms**: Exception during startup, application exits

**Common Causes and Solutions**:

1. **Port 8080 in use**: Another application is using port 8080
   ```bash
   # Find and kill the process
   lsof -i :8080  # macOS/Linux
   netstat -ano | findstr :8080  # Windows
   ```

2. **Missing dependencies**: Maven didn't download all libraries
   ```bash
   ./mvnw clean install -U  # -U forces dependency update
   ```

3. **Java version mismatch**: Wrong Java version
   ```bash
   java -version  # Must be 17 or 21
   ```

4. **Database connection**: Can't reach PostgreSQL (see previous section)

Always read the stack trace carefully—Spring Boot error messages usually pinpoint the exact problem.

---

## Next Steps

Now that your development environment is set up and you understand how the configuration works, you're ready to start
building the Library Management System:

**Milestone 1: Data Modeling** (See separate document)

- Create entity classes for User, Book, and Reservation
- Define relationships between entities
- Create repository interfaces
- Test that Hibernate generates correct tables

**Milestone 2: Authentication & User Management**

- Implement user registration endpoint
- Create JWT token generation
- Build login functionality
- Secure endpoints with Spring Security

**Milestone 3: Catalog Management**

- Implement book browsing with pagination
- Add search and filtering capabilities
- Create book detail endpoint

**Milestone 4: Reservation System**

- Build reservation creation
- Implement checkout process (Librarian only)
- Add return processing with late fee calculation
- Create borrowing history endpoint

**Milestone 5: Testing & Quality Assurance**

- Write unit tests with MockMVC
- Create integration tests with REST Assured
- Achieve >80% code coverage
- Validate all API contracts

**Milestone 6: AWS Deployment**

- Set up AWS RDS PostgreSQL
- Deploy to Elastic Beanstalk
- Configure production environment variables
- Verify production deployment

---

## Important Reminders

- **Never commit sensitive data** to version control (`.gitignore` is configured to exclude sensitive files)
- **Docker must be running** before starting the application—it won't connect to a stopped container
- **Hibernate creates tables automatically**—you don't write SQL DDL scripts
- **Use `application.properties` for local work**—don't modify `application-prod.properties` until AWS deployment
- **Generate a secure JWT secret for production**: `openssl rand -base64 32` (never use the dev secret in production)
- **Read error messages carefully**—Spring Boot provides detailed stack traces that usually reveal the exact problem

---

## Getting Help

If you encounter issues not covered in this guide:

1. **Check container status**: `docker ps` and `docker logs library-postgres`
2. **Read application logs**: Spring Boot prints detailed startup information
3. **Verify Java version**: `java -version` (must be 17 or 21)
4. **Test database connectivity**: Use psql or a GUI client to connect manually
5. **Review Hibernate logs**: Look for "create table" statements in console output
6. **Consult milestone documentation**: Each milestone has specific troubleshooting guidance
7. **Check Spring Boot documentation**: [docs.spring.io](https://docs.spring.io/spring-boot/docs/current/reference/)

Good luck building your Library Management System!