# Milestone 6: Deployment & Production Readiness

**Goal:** Deploy Spring Boot application to AWS Elastic Beanstalk with RDS PostgreSQL

---

## Deliverables

### 1. Application Preparation

**Build Production JAR:**
```bash
# Build the application without running tests
./mvnw clean package -DskipTests

# Verify JAR file was created
ls target/library-management-api-0.0.1-SNAPSHOT.jar
```

The production configuration is already set up in `application-prod.properties` which uses environment variables for all sensitive data.

---

### 2. Setting up RDS PostgreSQL Database

Navigate to **AWS Console → RDS → Create database**

**Engine Options:**
- Engine type: PostgreSQL
- Version: PostgreSQL 15.x
- Templates: Free tier (development) or Production

**Availability and Durability:**
- Deployment option: Single DB instance (for development)

**Database Settings:**
- DB instance identifier: `library-app-database`
- Master username: `postgres`
- Credentials management: Self managed
- Master password: Create and save a strong password securely

**Instance Configuration:**
- DB instance class: Burstable classes
- Select: `db.t4g.micro` (free tier eligible)

**Storage Configuration:**
- Storage type: General Purpose SSD (gp2)
- Allocated storage: `20` GiB
- Storage autoscaling: Optional (enable with max 100 GiB)

**Connectivity:**
- Compute resource: Don't connect to an EC2 compute resource
- Network type: IPv4
- Virtual Private Cloud (VPC): Default VPC
- DB subnet group: default
- Public access: No
- VPC security group: Choose existing → default
- Availability Zone: No preference

**Additional Configuration:**
- Initial database name: `librarydb`
- DB parameter group: default.postgres15
- Backup retention: 7 days (production) or 1 day (development)
- Encryption: Enable encryption at rest

**After Creation:**
- Wait 5-10 minutes for database to become "Available"
- Navigate to your database in RDS console
- Copy the **Endpoint** from Connectivity & security tab
- Format will be: `library-app-database.xxxxx.us-east-1.rds.amazonaws.com`
- Note the **Port**: 5432

---

### 3. Setting up Elastic Beanstalk Application

Navigate to **AWS Console → Elastic Beanstalk → Create application**

**Environment Tier:**
- Select: Web server environment

**Application Information:**
- Application name: `library-management-api`
- Application tags: (Optional) Add tags for organization

**Environment Information:**
- Environment name: `library-api-env`
- Domain: Leave blank (AWS will generate URL)
- Description: Library Management System REST API

**Platform Configuration:**
- Platform: Java
- Platform branch: Corretto 21 running on 64bit Amazon Linux 2023
- Platform version: 4.6.5 (Recommended) or latest

**Application Code:**
- Select: Upload your code
- Version label: `v1.0.0`
- Source code origin: Local file
- Choose file: Select your JAR from `target/` directory

**Presets:**
- Configuration presets: Single instance (free tier)

Click **"Next"** to configure more options

---

### 4. Service Access Configuration

**IAM Roles:**
- Service role: `aws-elasticbeanstalk-service-role`
   - If it doesn't exist, AWS will create it with default permissions
- EC2 instance profile: `aws-elasticbeanstalk-ec2-role`
   - If it doesn't exist, create with these policies:
      - AWSElasticBeanstalkWebTier
      - AWSElasticBeanstalkWorkerTier
      - AWSElasticBeanstalkMulticontainerDocker

**EC2 Key Pair:**
- Leave as default (not required for deployment)

---

### 5. Networking Configuration

**VPC Configuration:**
- VPC: Select default VPC (must match your RDS VPC)
- Instance settings:
   - Public IP address: Leave unchecked
- Instance subnets: Select at least one subnet:
   - us-east-1a (subnet-xxxxxxxxx)
   - us-east-1b (subnet-xxxxxxxxx)

**Database:**
- Enable database: Leave **UNCHECKED** (we're using existing RDS)

**Tags:**
- Optional: Add tags for resource management

---

### 6. Environment Properties Configuration

Configure these environment variables (critical for application to work):

| Name | Value | Description |
|------|-------|-------------|
| `SERVER_PORT` | `5000` | Elastic Beanstalk requires port 5000 |
| `SPRING_PROFILES_ACTIVE` | `prod` | Activates production configuration |
| `RDS_HOSTNAME` | `[your-rds-endpoint]` | From RDS console (step 2) |
| `RDS_PORT` | `5432` | PostgreSQL default port |
| `RDS_DB_NAME` | `librarydb` | Database name created in RDS |
| `RDS_USERNAME` | `postgres` | Master username from RDS setup |
| `RDS_PASSWORD` | `[your-password]` | Master password from RDS setup |
| `JWT_SECRET` | `[generate-secure-secret]` | Generate with: `openssl rand -base64 32` |

**To get your RDS endpoint:**
1. Go to RDS console
2. Click on `library-app-database`
3. Find endpoint in Connectivity & security section
4. Copy the full endpoint URL

**To generate JWT secret:**
```bash
openssl rand -base64 32
```
Copy the output and use it as `JWT_SECRET` value. Store it securely!

---

### 7. Review and Create

- Review all configuration settings
- Click **"Submit"** to create the environment
- Wait 5-10 minutes for environment creation

---

### 8. Security Group Configuration

After both RDS and Elastic Beanstalk are running:

**Update RDS Security Group:**
1. Navigate to **EC2 → Security Groups**
2. Find your RDS security group (check RDS instance details)
3. Click **Edit inbound rules**
4. Add inbound rule:
   - Type: PostgreSQL
   - Port: 5432
   - Source: Custom → Select Elastic Beanstalk security group
   - Description: "Allow EB to connect to RDS"
5. Save rules

This allows your Elastic Beanstalk application to connect to the RDS database.

---

## Post-Deployment Verification

### 1. Check Environment Health
- Go to Elastic Beanstalk console
- Environment health should show **green "Ok"** status
- If red, check logs for errors

### 2. Test Health Endpoint
```bash
curl http://[your-eb-url]/actuator/health
```
Expected: `{"status":"UP"}`

### 3. Access Swagger UI
Open in browser: `http://[your-eb-url]/swagger-ui/index.html`

### 4. Test API Endpoints

**Register a user:**
```bash
curl -X POST http://[your-eb-url]/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!@#",
    "firstName": "Test",
    "lastName": "User",
    "phoneNumber": "+1-555-0123"
  }'
```

**Login:**
```bash
curl -X POST http://[your-eb-url]/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test123!@#"
  }'
```

**Browse catalog (no auth required):**
```bash
curl http://[your-eb-url]/api/catalog/books
```

### 5. Verify Database Connection
- Check Elastic Beanstalk logs for database connection success
- Should see: "HikariPool started successfully"
- Should see: Hibernate table creation logs

---

## Troubleshooting

### Environment Health is Red
**Check:**
- Application logs in Elastic Beanstalk console
- Verify `SERVER_PORT=5000` in environment variables
- Confirm all environment variables are set correctly
- Look for startup errors in logs

### Database Connection Failed
**Check:**
- `RDS_HOSTNAME` matches your RDS endpoint exactly
- RDS security group allows inbound from EB security group
- Both RDS and EB are in the same VPC
- Database credentials are correct
- RDS instance status is "Available"

### Application Won't Start
**Check:**
- All environment variables are configured
- JAR file uploaded correctly
- Java version compatibility (Corretto 21)
- Review full stack trace in EB logs

### 404 on All Endpoints
**Check:**
- Application started successfully (check logs)
- `server.port=5000` configuration is active
- Controller mappings loaded (check logs for "Mapped" statements)

---

## Acceptance Criteria

- [ ] JAR file builds successfully
- [ ] RDS PostgreSQL database created with `librarydb` database
- [ ] RDS endpoint obtained and documented
- [ ] Elastic Beanstalk environment created successfully
- [ ] Environment health shows green "Ok" status
- [ ] All 8 environment variables configured correctly
- [ ] Security groups configured (EB can connect to RDS)
- [ ] Health endpoint returns 200 OK
- [ ] Swagger UI accessible
- [ ] Can register user successfully
- [ ] Can login and receive JWT token
- [ ] Can browse catalog without authentication
- [ ] Can create reservation with authentication
- [ ] LIBRARIAN can checkout and return books
- [ ] Database tables created automatically by Hibernate

---

## Technical Specifications

- Platform: Elastic Beanstalk Java (Corretto 21)
- Database: RDS PostgreSQL 15.x
- Instance: t3.small (single instance)
- Storage: 20GB RDS, 10GB EB
- Region: US East (us-east-1)
- Schema management: Hibernate DDL auto-update