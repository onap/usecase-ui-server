FROM nexus3.onap.org:10001/onap/integration-java11:9.0.0
MAINTAINER "Lu Ji" <lu.ji3@zte.com.cn>

# Expose the Usecase-UI backend port and postgreSQL port
EXPOSE 8082

# set env parameters
ENV PG_HOME=/usr/share/postgresql \
    PG_VERSION=13.4 \
    PG_VAR_LIB=/var/lib/postgresql \
    PG_USR_LIB=/usr/lib/postgresql \
    PG_LOGDIR=/var/log/postgresql

#Install PostgreSQL as user root, Common users do not have permission to perform the installation  
USER root
RUN apk update && apk add postgresql && apk add bash

# Adjust PostgreSQL configuration
RUN cp ${PG_HOME}/pg_hba.conf.sample ${PG_HOME}/pg_hba.conf && \
    echo "host all all 0.0.0.0/0 md5" >> ${PG_HOME}/pg_hba.conf && \
    cp ${PG_HOME}/postgresql.conf.sample ${PG_HOME}/postgresql.conf && \
    cp ${PG_HOME}/pg_ident.conf.sample ${PG_HOME}/pg_ident.conf && \
    echo "listen_addresses='*'" >> ${PG_HOME}/postgresql.conf && \
    echo "data_directory = '/usr/share/postgresql/data'" >> ${PG_HOME}/postgresql.conf && \
    echo "hba_file = '/usr/share/postgresql/pg_hba.conf'" >> ${PG_HOME}/postgresql.conf && \
    echo "ident_file = '/usr/share/postgresql/pg_ident.conf'" >> ${PG_HOME}/postgresql.conf && \
    mkdir ${PG_HOME}/data && \
    mkdir /run/postgresql && \
    chown -R postgres:postgres ${PG_HOME} && \
    chown -R postgres:postgres /run/postgresql

#init databases,Use the non-root user  
USER postgres
RUN initdb -D /usr/share/postgresql/data
   
USER root
# Add VOLUMEs to allow backup of config, logs and databases
VOLUME ["${PG_HOME}", "${PG_VAR_LIB}", "${PG_LOGDIR}"]

# Set the default command to run postgreSQL when starting the container
# CMD ["${PG_USR_LIB}/${PG_VERSION}/bin/postgres", "-D", "${PG_VAR_LIB}/${PG_VERSION}/main", "-c", "config_file=${PG_HOME}/${PG_VERSION}/main/postgresql.conf"]

#Configure Java SDK
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib

#Add Usecase-UI server related resources to the docker image
RUN mkdir /home/uui
WORKDIR /home/uui
ADD usecase-ui-server-*-linux64.tar.gz /home/uui/

RUN source /home/uui/bin/docker-env-config.sh
USER postgres
WORKDIR /home/uui
ENTRYPOINT /home/uui/bin/run.sh
