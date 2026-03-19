#!/bin/bash

# 🏢 Jakarta EE WebApp - Setup e Deploy Script
# Este script contém todos os comandos necessários para configurar e executar o projeto

echo "🏢 Jakarta EE WebApp - Setup Script"
echo "===================================="
echo ""

# ========================================
# 1. NAVEGAÇÃO E SETUP INICIAL
# ========================================

echo "📁 Navegando para o diretório do projeto..."
cd /home/claude/jakarta-ee-webapp

echo "🔧 Inicializando Git..."
git init
git add .
git commit -m "🎉 Initial commit - Jakarta EE with CI/CD"

echo ""
echo "✅ Setup inicial concluído!"
echo ""

# ========================================
# 2. CRIAR REPOSITÓRIO NO GITHUB (MANUAL)
# ========================================

echo "📋 PRÓXIMO PASSO - Criar repositório no GitHub:"
echo ""
echo "1. Acesse: https://github.com/new"
echo "2. Repository name: jakarta-ee-webapp"
echo "3. Description: Enterprise Java application using Jakarta EE with CI/CD"
echo "4. Public"
echo "5. NÃO marque 'Initialize with README'"
echo "6. Clique em 'Create repository'"
echo ""
echo "Pressione ENTER quando criar o repositório..."
read

# ========================================
# 3. CONECTAR E PUSH PARA O GITHUB
# ========================================

echo "🚀 Configurando remote e fazendo push..."

# Configure seu usuário GitHub aqui
GITHUB_USER="tharsis-soares"

git remote add origin git@github.com:${GITHUB_USER}/jakarta-ee-webapp.git
git branch -M main
git push -u origin main

echo ""
echo "✅ Push realizado com sucesso!"
echo ""

# ========================================
# 4. TESTAR LOCALMENTE COM DOCKER
# ========================================

echo "🐳 Comandos para testar localmente (execute na sua máquina):"
echo ""
echo "# Build da aplicação"
echo "mvn clean package"
echo ""
echo "# Subir containers (PostgreSQL + WildFly)"
echo "docker-compose up -d"
echo ""
echo "# Ver logs"
echo "docker-compose logs -f wildfly"
echo ""
echo "# Testar API"
echo "curl http://localhost:8080/jakarta-ee-webapp/api/health"
echo ""
echo "# Parar containers"
echo "docker-compose down"
echo ""

# ========================================
# 5. VERIFICAR CI/CD NO GITHUB
# ========================================

echo "✅ Verificar CI/CD Pipeline:"
echo ""
echo "Acesse: https://github.com/${GITHUB_USER}/jakarta-ee-webapp/actions"
echo ""
echo "O pipeline deve executar automaticamente após o push."
echo ""

# ========================================
# 6. CONFIGURAR SECRETS PARA DOCKER HUB (OPCIONAL)
# ========================================

echo "🔐 OPCIONAL - Configurar Docker Hub secrets:"
echo ""
echo "1. Acesse: https://github.com/${GITHUB_USER}/jakarta-ee-webapp/settings/secrets/actions"
echo "2. Clique em 'New repository secret'"
echo "3. Adicione:"
echo "   - DOCKER_USERNAME: seu_usuario_dockerhub"
echo "   - DOCKER_PASSWORD: seu_token_dockerhub"
echo ""

# ========================================
# 7. COMANDOS ÚTEIS
# ========================================

echo "📚 Comandos úteis:"
echo ""
echo "# Usar Makefile (se tiver make instalado)"
echo "make help         # Ver todos os comandos"
echo "make build        # Build da aplicação"
echo "make test         # Rodar testes"
echo "make docker-up    # Subir containers"
echo "make deploy       # Build + Deploy"
echo ""

echo "🎉 Setup completo!"