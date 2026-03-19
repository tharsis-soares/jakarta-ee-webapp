#!/bin/bash

echo "🧪 Testando Jakarta EE API"
echo "=========================="
echo ""

# Limpar banco
echo "🗑️  Limpando banco de dados..."
docker-compose down -v
docker-compose up -d

echo "⏳ Aguardando WildFly iniciar (60s)..."
sleep 60

BASE_URL="http://localhost:8080/jakarta-ee-webapp/api"

echo ""
echo "📋 1. Criar Customer 1"
curl -X POST ${BASE_URL}/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Acme Corp",
    "email": "contact@acme.com",
    "taxId": "12.345.678/0001-90"
  }'

echo ""
echo ""
echo "📋 2. Criar Customer 2"
curl -X POST ${BASE_URL}/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "TechCorp Brasil",
    "email": "contato@techcorp.com.br",
    "taxId": "98.765.432/0001-10"
  }'

echo ""
echo ""
echo "📋 3. Listar todos os Customers"
curl ${BASE_URL}/customers

echo ""
echo ""
echo "📋 4. Criar Invoice para Customer 1"
curl -X POST ${BASE_URL}/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "amount": 1500.00,
    "description": "Professional Services - Q1 2026",
    "dueDate": "2026-04-15"
  }'

echo ""
echo ""
echo "📋 5. Criar Invoice para Customer 2"
curl -X POST ${BASE_URL}/invoices \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 2,
    "amount": 3200.50,
    "description": "Software Development - March 2026",
    "dueDate": "2026-04-30"
  }'

echo ""
echo ""
echo "📋 6. Listar todas as Invoices"
curl ${BASE_URL}/invoices

echo ""
echo ""
echo "✅ Testes completos!"