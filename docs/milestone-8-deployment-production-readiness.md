### Milestone 6: Deployment & Production Readiness

**Goal:** Deploy to AWS Elastic Beanstalk with RDS PostgreSQL

#### Deliverables:

1. **Application Preparation**

   **Build Production JAR:**
   - Build Spring Boot application: `./mvnw clean package -DskipTests`
   - Verify JAR file created in `target/` directory
   - JAR naming convention: `[app-name]-0.0.1-SNAPSHOT.jar`
   - Ensure application.properties configured for production profile

   **Production application-prod.properties:**
   ```properties
   # Default profile
   spring.profiles.active=${SPRING_PROFILES_ACTIVE:dev}
   server.port=${SERVER_PORT:8080}
   
   # Production profile (activated via environment variable)
   spring.config.activate.on-profile=prod
   
   # Database Configuration (uses environment variables)
   spring.datasource.url=jdbc:postgresql://${RDS_HOSTNAME}:${RDS_PORT}/${RDS_DB_NAME}
   spring.datasource.username=${RDS_USERNAME}
   spring.datasource.password=${RDS_PASSWORD}
   spring.datasource.driver-class-name=org.postgresql.Driver
   
   # JPA Configuration
   spring.jpa.hibernate.ddl-auto=validate
   spring.jpa.show-sql=false
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   
   # JWT Configuration
   jwt.secret=${JWT_SECRET}
   jwt.expiration=86400
   
   # Actuator Configuration
   management.endpoints.web.exposure.include=health
   management.endpoint.health.show-details=when-authorized
   
   # Logging Configuration
   logging.level.root=INFO
   logging.level.com.library=INFO
   logging.level.org.springframework.web=INFO
   ```

2. **RDS PostgreSQL Database Setup**

   **Create RDS Instance:**
   - Navigate to AWS Console → RDS → Create database
   - Engine: PostgreSQL 15.x
   - Templates: Free tier (for development) or Production (for production)

   **Database Settings:**
   - DB instance identifier: `library-app-database`
   - Master username: `postgres`
   - Credentials management: Self managed
   - Master password: Create strong password (store securely)

   **Instance Configuration:**
   - DB instance class: Burstable classes
   - Choose: `db.t4g.micro` (free tier) or `db.t3.small` (production)

   **Storage Configuration:**
   - Storage type: General Purpose SSD (gp2)
   - Allocated storage: 20 GiB
   - Storage autoscaling: Enable (optional, max 100 GiB)

   **Connectivity:**
   - Compute resource: Don't connect to an EC2 compute resource
   - Network type: IPv4
   - Virtual Private Cloud (VPC): Default VPC
   - DB subnet group: default
   - Public access: No (for security)
   - VPC security group: Choose existing → default
   - Availability Zone: No preference (or us-east-1a)

   **Additional Configuration:**
   - Initial database name: `librarydb`
   - DB parameter group: default.postgres15
   - Backup retention period: 7 days (production) or 1 day (development)
   - Encryption: Enable encryption at rest

   **Post-Creation:**
   - Wait 5-10 minutes for database creation
   - Copy endpoint from RDS console (Connectivity & security tab)
   - Format: `library-app-database.xxxxx.us-east-1.rds.amazonaws.com`
   - Note the port: 5432

3. **Elastic Beanstalk Application Setup**

   **Create Application:**
   - Navigate to AWS Console → Elastic Beanstalk → Create application

   **Environment Tier:**
   - Select: Web server environment

   **Application Information:**
   - Application name: `library-management-api`
   - Application tags: (optional) Environment=Production, Project=LibraryApp

   **Environment Information:**
   - Environment name: `library-api-env`
   - Domain: Leave blank (auto-generated URL)
   - Description: Library Management System REST API

   **Platform Configuration:**
   - Platform: Java
   - Platform branch: Corretto 21 running on 64bit Amazon Linux 2023
   - Platform version: 4.6.5 (Recommended) or latest available

   **Application Code:**
   - Select: Upload your code
   - Version label: `v1.0.0`
   - Source code origin: Local file
   - Choose file: Select your JAR file from `target/` directory

   **Presets:**
   - Configuration presets: Single instance (free tier) or High availability (production)

   **Click "Next" to Configure more options**

4. **Service Access Configuration**

   **IAM Roles:**
   - Service role: `aws-elasticbeanstalk-service-role`
      - If not exists, create new service role with default permissions
   - EC2 instance profile: `aws-elasticbeanstalk-ec2-role`
      - If not exists, create new EC2 role with these permissions:
         - AWSElasticBeanstalkWebTier
         - AWSElasticBeanstalkWorkerTier
         - AWSElasticBeanstalkMulticontainerDocker

   **EC2 Key Pair:**
   - Leave as "Choose a key pair" (not required for deployment)

5. **Networking, Database, and Tags Configuration**

   **VPC Configuration:**
   - VPC: Select default VPC (must match RDS VPC)
   - Instance settings:
      - Public IP address: Uncheck "Activated" (better security)
   - Instance subnets: Select at least one:
      - us-east-1a (subnet-xxxxxxxxx)
      - us-east-1b (subnet-xxxxxxxxx)

   **Database:**
   - Enable database: Leave UNCHECKED (using existing RDS)

   **Tags:**
   - (Optional) Add tags for resource management

6. **Instance Traffic and Scaling Configuration**

   **Instances:**
   - Root volume type: General Purpose (SSD)
   - Size: 10 GB
   - Instance types: t3.small or t3.medium

   **Capacity:**
   - Environment type: Single instance (development) or Load balanced (production)
   - For Load balanced:
      - Min instances: 1
      - Max instances: 4
      - Fleet composition: On-Demand instances

   **Load Balancer:**
   - For production: Application Load Balancer
   - Processes: default (port 80)

7. **Updates, Monitoring, and Managed Updates**

   **Rolling Updates:**
   - Deployment policy: Rolling (for zero-downtime updates)
   - Batch size: 25%

   **Monitoring:**
   - Health reporting: Enhanced
   - Managed updates: (Optional) Enable for automatic platform updates

8. **Environment Properties Configuration**

   Configure the following environment variables:

   | Name | Value | Description |
      |------|-------|-------------|
   | `SERVER_PORT` | `5000` | Elastic Beanstalk expects port 5000 |
   | `SPRING_PROFILES_ACTIVE` | `prod` | Activate production profile |
   | `RDS_HOSTNAME` | `library-app-database.xxxxx.us-east-1.rds.amazonaws.com` | RDS endpoint (from RDS console) |
   | `RDS_PORT` | `5432` | PostgreSQL port |
   | `RDS_DB_NAME` | `librarydb` | Database name |
   | `RDS_USERNAME` | `postgres` | Master username |
   | `RDS_PASSWORD` | `[your-password]` | Master password (from RDS setup) |
   | `JWT_SECRET` | `[generate-secure-secret]` | Min 256-bit secret for JWT signing |

   **JWT Secret Generation:**
   - Generate using: `openssl rand -base64 32`
   - Store securely in password manager
   - Never commit to version control

9. **Security Group Configuration**

   **Update RDS Security Group:**
   - Navigate to EC2 → Security Groups
   - Find your RDS security group (sg-xxxxx)
   - Add inbound rule:
      - Type: PostgreSQL
      - Port: 5432
      - Source: Elastic Beanstalk security group (sg-xxxxx)
      - Description: Allow EB instances to connect to RDS

   **Elastic Beanstalk Security Group:**
   - Automatically configured
   - Allows HTTP traffic on port 80
   - Allows traffic on application port 5000

10. **Documentation**

    **API Documentation (Swagger UI):**
   - Accessible at: `http://[your-eb-url]/swagger-ui/index.html`
   - SpringDoc OpenAPI 3 configuration in application
   - All 11 endpoints documented with schemas
   - Bearer authentication configured

    **Deployment Documentation:**
   - AWS account prerequisites
   - RDS endpoint retrieval steps
   - Environment variable configuration
   - Rollback procedures
   - Troubleshooting guide

    **Architecture Diagram:**
   - Client → Elastic Beanstalk (Load Balancer) → EC2 Instances → RDS PostgreSQL
   - VPC layout with subnets
   - Security group relationships

#### Acceptance Criteria:
- [ ] JAR file builds successfully with `./mvnw clean package`
- [ ] RDS PostgreSQL database created and accessible
- [ ] Initial database `librarydb` created in RDS instance
- [ ] Elastic Beanstalk application created successfully
- [ ] Application deployed with version label v1.0.0
- [ ] Environment health shows "Ok" (green) status
- [ ] Health check endpoint returns 200 OK: `http://[eb-url]/actuator/health`
- [ ] All 11 endpoints accessible via Elastic Beanstalk URL
- [ ] Swagger UI accessible: `http://[eb-url]/swagger-ui/index.html`
- [ ] Can register user, login, and receive JWT token
- [ ] Can browse catalog without authentication
- [ ] Can create reservation with authentication
- [ ] LIBRARIAN can checkout and return books
- [ ] Application logs visible in Elastic Beanstalk console
- [ ] Database connection successful (verify in logs)
- [ ] Flyway migrations executed successfully on startup

#### Deployment Steps:

**Pre-Deployment:**
1. Run all tests locally: `./mvnw test`
2. Verify test coverage >80%
3. Build production JAR: `./mvnw clean package -DskipTests`
4. Verify JAR size and structure

**RDS Setup:**
1. Create RDS PostgreSQL instance (5-10 minutes)
2. Note endpoint, port, database name, credentials
3. Verify database is in "Available" state
4. Test connection using pgAdmin or psql (optional)

**Elastic Beanstalk Setup:**
1. Create application with Java platform
2. Upload JAR file
3. Configure networking (same VPC as RDS)
4. Configure service roles
5. Add environment variables (database, JWT)
6. Review and create environment (5-10 minutes)

**Post-Deployment Verification:**
1. Check environment health (should be green)
2. Review deployment logs for errors
3. Test health endpoint: `curl http://[eb-url]/actuator/health`
4. Test registration: `POST /api/auth/register`
5. Test login and token generation
6. Test catalog browsing
7. Test reservation flow
8. Verify database records in RDS

**Security Group Update:**
1. Navigate to EC2 console
2. Find RDS security group
3. Add inbound rule from EB security group
4. Verify application can connect to database

#### Troubleshooting Guide:

**Environment Health is Red:**
- Check application logs in EB console
- Verify SERVER_PORT=5000 in environment variables
- Check if application started successfully
- Review Flyway migration errors

**Database Connection Failed:**
- Verify RDS_HOSTNAME is correct endpoint
- Check RDS security group allows EB security group
- Verify VPC configuration matches
- Check database credentials are correct
- Ensure RDS is in "Available" state

**Application Won't Start:**
- Review full logs in EB console
- Check for missing environment variables
- Verify JAR file uploaded correctly
- Check Java version compatibility
- Review Flyway migration errors

**404 on All Endpoints:**
- Verify application started successfully
- Check server.port=5000 configuration
- Review application context path
- Check controller mappings in logs

#### Cost Estimates (Monthly - US East):
- **Development:**
   - RDS db.t4g.micro (Free tier: first 12 months): $0
   - RDS db.t4g.micro (after free tier): ~$15
   - EB t3.small single instance: ~$15
   - Storage and data transfer: ~$5
   - **Total: $20-35/month**

- **Production:**
   - RDS db.t3.small: ~$30
   - EB t3.medium (2 instances + ALB): ~$60
   - Storage, backups, data transfer: ~$15
   - **Total: ~$105/month**

#### Technical Specifications:
- Platform: Elastic Beanstalk Java (Corretto 21)
- Database: RDS PostgreSQL 15.x
- Instance type: t3.small (development) or t3.medium (production)
- Storage: 20GB for RDS, 10GB for EB instances
- Region: US East (us-east-1) or your preferred region