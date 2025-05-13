#!/bin/bash

# Variables para conexión local a Aiven
export SPRING_DATASOURCE_URL="jdbc:mysql://baskettrainer-db-baskettrainer-db.k.aivencloud.com:27546/defaultdb?ssl-mode=VERIFY_CA&useSSL=true"
export SPRING_DATASOURCE_USERNAME="avnadmin"
export SPRING_DATASOURCE_PASSWORD="[AIVEN_PASSWORD]"
export SPRING_DATASOURCE_DRIVER_CLASS_NAME="com.mysql.cj.jdbc.Driver"
export SPRING_JPA_HIBERNATE_DDL_AUTO="update"
export SPRING_JPA_SHOW_SQL="true"
export SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT="org.hibernate.dialect.MySQLDialect"
export JAVAX_NET_SSL_TRUSTSTORE="src/main/resources/ssl/truststore.jks"
export JAVAX_NET_SSL_TRUSTSTOREPASSWORD="changeit"