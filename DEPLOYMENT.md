# Deployment Guide for Render.com

This guide explains how to deploy the Message Service on Render.com.

## Prerequisites

1. A Render.com account
2. A PostgreSQL database (you can use Render's managed PostgreSQL service)

## Environment Variables

Set the following environment variables in your Render.com service configuration:

### Required Variables

- `DATABASE_URL`: Your PostgreSQL connection string
  - Format: `jdbc:postgresql://host:port/database_name`
  - Example: `jdbc:postgresql://dpg-abc123-a.frankfurt-postgres.render.com/mydb`

- `DATABASE_USERNAME`: Your PostgreSQL username
  - Example: `myuser`

- `DATABASE_PASSWORD`: Your PostgreSQL password
  - Example: `mypassword123`

### Optional Variables

- `PORT`: The port your application will run on (default: 8080)
  - Render.com will automatically set this to their assigned port

## Deployment Steps

### 1. Create a PostgreSQL Database

1. Go to your Render.com dashboard
2. Click "New" → "PostgreSQL"
3. Choose a name for your database
4. Select your preferred region
5. Click "Create Database"
6. Note down the connection details (URL, username, password)

### 2. Deploy the Application

1. Go to your Render.com dashboard
2. Click "New" → "Web Service"
3. Connect your GitHub repository
4. Configure the service:
   - **Name**: `message-service` (or your preferred name)
   - **Environment**: `Docker`
   - **Region**: Choose the same region as your database
   - **Branch**: `main` (or your default branch)
   - **Build Command**: Leave empty (Docker will handle this)
   - **Start Command**: Leave empty (Docker will handle this)

### 3. Set Environment Variables

1. In your web service settings, go to the "Environment" tab
2. Add the following environment variables:
   ```
   DATABASE_URL=jdbc:postgresql://your-db-host:5432/your-db-name
   DATABASE_USERNAME=your-db-username
   DATABASE_PASSWORD=your-db-password
   ```

### 4. Deploy

1. Click "Create Web Service"
2. Render will automatically build and deploy your application
3. Monitor the build logs for any issues

## Health Check

Your service will be available at the URL provided by Render.com. You can test it by:

1. Making a GET request to `https://your-service-name.onrender.com/health` (if you have a health endpoint)
2. Or making a POST request to `https://your-service-name.onrender.com/api/messages` with a JSON body

## Troubleshooting

### Common Issues

1. **Database Connection Failed**: 
   - Verify your `DATABASE_URL` is correct
   - Ensure your database is in the same region as your web service
   - Check that your database credentials are correct

2. **Build Failed**:
   - Check the build logs in Render.com
   - Ensure your Dockerfile is in the root directory
   - Verify all required files are present
   - Make sure the Maven wrapper files (`.mvn/`, `mvnw`, `mvnw.cmd`) are included in your repository

3. **Application Won't Start**:
   - Check the application logs in Render.com
   - Verify all environment variables are set correctly
   - Ensure the port configuration is correct

4. **Maven Wrapper Issues**:
   - If you see errors about missing `.mvn` directory or `mvnw` files, ensure these files are committed to your repository
   - The Maven wrapper files are required for the Docker build to work properly

### Logs

You can view application logs in the Render.com dashboard under your service's "Logs" tab.

## Security Notes

- Never commit sensitive credentials to your repository
- Use Render.com's environment variable system for all secrets
- Consider using Render.com's managed PostgreSQL service for better security
- Regularly rotate your database passwords 