FROM quay.io/wildfly/wildfly:27.0.0.Final-jdk17

USER root
RUN curl -o /tmp/postgresql.jar https://jdbc.postgresql.org/download/postgresql-42.7.1.jar

COPY wildfly-setup.sh /tmp/wildfly-setup.sh
RUN chmod +x /tmp/wildfly-setup.sh

USER jboss
RUN /tmp/wildfly-setup.sh

COPY target/jakarta-ee-webapp.war /opt/jboss/wildfly/standalone/deployments/jakarta-ee-webapp.war

EXPOSE 8080 9990
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
