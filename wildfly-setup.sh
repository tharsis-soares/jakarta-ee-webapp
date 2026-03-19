#!/bin/bash
set -e

/opt/jboss/wildfly/bin/standalone.sh &
WILDFLY_PID=$!

echo "Aguardando WildFly iniciar..."
until /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="version" > /dev/null 2>&1; do
    sleep 3
done

echo "Instalando módulo PostgreSQL..."
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="module add --name=org.postgresql --resources=/tmp/postgresql.jar --dependencies=java.se"

echo "Registrando driver..."
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="/subsystem=datasources/jdbc-driver=postgresql:add(driver-name=postgresql,driver-module-name=org.postgresql,driver-class-name=org.postgresql.Driver)"

echo "Criando datasource..."
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="data-source add --name=PostgresDS --jndi-name=java:jboss/datasources/PostgresDS --driver-name=postgresql --connection-url=jdbc:postgresql://postgres:5432/invoicedb --user-name=postgres --password=postgres --enabled=true"

echo "Shutdown..."
/opt/jboss/wildfly/bin/jboss-cli.sh --connect --command=":shutdown"

wait $WILDFLY_PID
echo "Configuração concluída!"
