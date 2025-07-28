#!/bin/bash

echo "🏭 Starting Unified Clothes Warehouse Management System..."
echo "========================================================"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven."
    exit 1
fi

echo "✅ Java and Maven are installed"

# Clean and build the project
echo "🔨 Building the unified project..."
mvn clean package -DskipTests

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the errors above."
    exit 1
fi

echo "✅ Build successful"

# Start the application
echo "🚀 Starting the unified application..."
echo ""
echo "📋 Application will be available at:"
echo "   🌐 Main Application: http://localhost:8080"
echo "   🗄️  H2 Database Console: http://localhost:8080/h2-console"
echo "   📊 Health Check: http://localhost:8080/actuator/health"
echo "   🔌 REST API: http://localhost:8080/api/distribution-centres"
echo ""
echo "🔐 Web Login Credentials:"
echo "   👑 Admin: admin / admin123 (Full access + distribution centre management)"
echo "   👷 Employee: employee / emp123 (Inventory management)"
echo "   👤 User: user / user123 (View only)"
echo ""
echo "🔑 API Access (Basic Auth):"
echo "   🏢 Distribution Centre Manager: dcmanager / dcmanager123"
echo "   👑 Admin API Access: admin / admin123"
echo ""
echo "🎯 Test Distance Logic:"
echo "   1. Login as admin"
echo "   2. Go to Admin Panel"
echo "   3. Request 'Designer Jacket' (Balenciaga) - available in multiple centres"
echo "   4. System will select North York (closest at 15.2 km)"
echo ""
echo "🛑 Press Ctrl+C to stop the application"
echo "========================================================"

java -jar target/clothes-warehouse-unified-1.0.0.jar
