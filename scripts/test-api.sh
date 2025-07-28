#!/bin/bash

BASE_URL="http://localhost:8081/api"
AUTH="admin:admin123"

echo "Testing Distribution Centre API..."
echo ""

echo "1. Getting all distribution centres:"
curl -u $AUTH -X GET $BASE_URL/distribution-centres | jq .
echo ""

echo "2. Getting distribution centre by ID (1):"
curl -u $AUTH -X GET $BASE_URL/distribution-centres/1 | jq .
echo ""

echo "3. Testing item request from centre 1:"
curl -u $AUTH -X POST $BASE_URL/distribution-centres/1/request-item \
  -H "Content-Type: application/json" \
  -d '{"name": "Designer Jacket", "brand": "BALENCIAGA"}'
echo ""

echo "4. Getting items from distribution centre 1:"
curl -u $AUTH -X GET $BASE_URL/distribution-centres/1/items | jq .
echo ""
