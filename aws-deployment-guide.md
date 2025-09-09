# AWS Deployment Guide - BuscaPQ Application

## Option 1: AWS Elastic Beanstalk (Recommended for beginners)

### Backend Deployment:
1. **Build the application:**
   ```bash
   cd backend
   ./mvnw clean package -DskipTests
   ```

2. **Create Elastic Beanstalk Application:**
   - Go to AWS Elastic Beanstalk console
   - Create new application: "buscapq-backend"
   - Platform: Java 17 (Corretto)
   - Upload `backend/target/linkedinsearch-0.0.1-SNAPSHOT.jar`

3. **Environment Variables:**
   - `SPRING_PROFILES_ACTIVE=prod`
   - `DATABASE_URL=jdbc:postgresql://your-rds-endpoint:5432/buscapq`
   - `DATABASE_USERNAME=your-db-username`
   - `DATABASE_PASSWORD=your-db-password`

### Frontend Deployment:
1. **Update API URL in frontend/script.js:**
   ```javascript
   const API_BASE_URL = 'https://your-backend-url.elasticbeanstalk.com/api';
   ```

2. **Deploy to S3 + CloudFront:**
   - Create S3 bucket: "buscapq-frontend"
   - Upload frontend files (index.html, styles.css, script.js)
   - Enable static website hosting
   - Create CloudFront distribution

## Option 2: AWS ECS with Fargate

### Prerequisites:
- Docker installed locally
- AWS CLI configured

### Steps:
1. **Build and push Docker image:**
   ```bash
   # Build the JAR
   cd backend && ./mvnw clean package -DskipTests
   
   # Build Docker image
   docker build -t buscapq-backend .
   
   # Tag and push to ECR
   aws ecr create-repository --repository-name buscapq-backend
   docker tag buscapq-backend:latest your-account.dkr.ecr.region.amazonaws.com/buscapq-backend:latest
   docker push your-account.dkr.ecr.region.amazonaws.com/buscapq-backend:latest
   ```

2. **Create ECS Task Definition and Service**

## Database Setup (AWS RDS):
1. Create PostgreSQL RDS instance
2. Update backend dependencies in pom.xml:
   ```xml
   <dependency>
       <groupId>org.postgresql</groupId>
       <artifactId>postgresql</artifactId>
       <scope>runtime</scope>
   </dependency>
   ```

## Security Considerations:
- Use AWS Systems Manager Parameter Store for secrets
- Configure Security Groups properly
- Enable HTTPS with SSL certificates
- Update CORS configuration for production domains

## Cost Optimization:
- Use t3.micro instances for testing
- Consider AWS Lambda for serverless deployment
- Use CloudFront for CDN to reduce costs
