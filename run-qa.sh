#!/bin/bash

echo "🏭 Starting Unified Clothes Warehouse System (QA Mode with PostgreSQL)"
echo "====================================================================="

# Check if Docker is installed and running
if ! command -v docker &> /dev/null; then
    echo "❌ Docker is not installed. Please install Docker."
    exit 1
fi

if ! docker info &> /dev/null; then
    echo "❌ Docker is not running. Please start Docker."
    exit 1
fi

echo "✅ Docker is installed and running"

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven."
    exit 1
fi

echo "✅ Maven is installed"

# Clean and build the project
echo "🔨 Building the unified project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi

echo "✅ Build successful"

# Start PostgreSQL with Docker
echo "🐘 Starting PostgreSQL database..."
docker-compose up postgres -d

# Wait for PostgreSQL to be ready
echo "⏳ Waiting for PostgreSQL to be ready..."
sleep 15

# Check if PostgreSQL is ready
until docker-compose exec postgres pg_isready -U postgres; do
    echo "⏳ Waiting for PostgreSQL..."
    sleep 2
done

echo "✅ PostgreSQL is ready"

# Start the application with QA profile
echo "🚀 Starting the unified application in QA mode..."
echo ""
echo "📋 Application will be available at:"
echo "   🌐 Main Application: http://localhost:8080"
echo "   🗄️  PostgreSQL: localhost:5432"
echo "   📊 Health Check: http://localhost:8080/actuator/health"
echo "   🔌 REST API: http://localhost:8080/api/distribution-centres"
echo ""
echo "🔐 Login Credentials (same as dev mode):"
echo "   👑 Admin: admin / admin123"
echo "   👷 Employee: employee / emp123"
echo "   👤 User: user / user123"
echo "   🏢 API: dcmanager / dcmanager123"
echo ""
echo "🛑 Press Ctrl+C to stop the application"
echo "====================================================================="

SPRING_PROFILES_ACTIVE=qa java -jar target/clothes-warehouse-unified-1.0.0.jar
