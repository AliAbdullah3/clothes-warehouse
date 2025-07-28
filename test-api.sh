#!/bin/bash

echo "🧪 Testing Unified Clothes Warehouse API"
echo "========================================"

BASE_URL="http://localhost:8080/api"
AUTH="dcmanager:dcmanager123"
ADMIN_AUTH="admin:admin123"

echo "1. 🏥 Health Check:"
curl -s http://localhost:8080/actuator/health | jq .
echo ""

echo "2. 🏢 Getting all distribution centres (DC Manager access):"
curl -s -u $AUTH -X GET $BASE_URL/distribution-centres | jq .
echo ""

echo "3. 🏢 Getting distribution centre by ID (1):"
curl -s -u $AUTH -X GET $BASE_URL/distribution-centres/1 | jq .
echo ""

echo "4. 📦 Getting items from distribution centre 1:"
curl -s -u $AUTH -X GET $BASE_URL/distribution-centres/1/items | jq .
echo ""

echo "5. 🛒 Testing item request from centre 1 (Admin access required):"
curl -s -u $ADMIN_AUTH -X POST $BASE_URL/distribution-centres/1/request-item \
  -H "Content-Type: application/json" \
  -d '{"name": "Designer Jacket", "brand": "BALENCIAGA"}'
echo ""

echo "6. 📊 Testing distance logic - requesting item available in multiple centres:"
echo "   This should select North York (closest at 15.2 km)"
curl -s -u $ADMIN_AUTH -X POST $BASE_URL/distribution-centres/1/request-item \
  -H "Content-Type: application/json" \
  -d '{"name": "Designer Jacket", "brand": "BALENCIAGA"}'
echo ""

echo "7. 🆕 Adding new item to distribution centre 2 (Admin access):"
curl -s -u $ADMIN_AUTH -X POST $BASE_URL/distribution-centres/2/items \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Item",
    "brand": "GUCCI",
    "yearOfCreation": 2023,
    "price": 1500.0,
    "quantity": 5
  }' | jq .
echo ""

echo "✅ API testing completed!"
echo ""
echo "📍 Distribution Centre Locations (from CN Tower warehouse):"
echo "   🏢 North York: 15.2 km (closest)"
echo "   🏢 Mississauga: 22.1 km"
echo "   🏢 Scarborough: 28.5 km"
echo "   🏢 Markham: 31.8 km (furthest)"
