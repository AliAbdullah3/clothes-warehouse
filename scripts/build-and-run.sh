#!/bin/bash

echo "Building Distribution Centre API..."
mvn clean package -DskipTests

echo "Starting Docker containers..."
docker-compose up --build -d

echo "Waiting for services to start..."
sleep 30

echo "Distribution Centre API is running on http://localhost:8081"
echo "PostgreSQL is running on localhost:5432"
echo ""
echo "API Endpoints:"
echo "GET    /api/distribution-centres"
echo "POST   /api/distribution-centres"
echo "GET    /api/distribution-centres/{id}"
echo "POST   /api/distribution-centres/{id}/items"
echo "POST   /api/distribution-centres/{id}/request-item"
echo ""
echo "Basic Auth Credentials:"
echo "Username: admin"
echo "Password: admin123"
