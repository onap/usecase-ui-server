FROM ubuntu:16.04
MAINTAINER "Lu Ji" <lu.ji3@zte.com.cn>

# Expose the Usecase-UI backend port and postgreSQL port
EXPOSE 8082

# set env parameters
ENV PG_HOME=/etc/postgresql \
    PG_VERSION=9.5 \
    PG_VAR_LIB=/var/lib/postgresql \
    PG_USR_LIB=/usr/lib/postgresql \
    PG_LOGDIR=/var/log/postgresql

# Add the PostgreSQL PGP key to verify their Debian packages
RUN apt-key adv --keyserver keyserver.ubuntu.com --recv-keys B97B0AFCAA1A47F044F244A07FCC7D46ACCC4CF8
# Add PostgreSQL's repository
RUN echo "deb http://apt.postgresql.org/pub/repos/apt/ precise-pgdg main" > /etc/apt/sources.list.d/pgdg.list

# Update the Ubuntu and install tools
add-apt-repository ppa:openjdk-r/ppa \
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get install -y gcc && \
    apt-get install -y curl && \
    apt-get install -y vim && \
    apt-get install -y build-essential && \
    apt-get install -y libssl-dev && \
    apt-get install -y libffi-dev && \
    apt-get -y -q install postgresql-${PG_VERSION} postgresql-client-${PG_VERSION} postgresql-contrib-${PG_VERSION}

# Adjust PostgreSQL configuration
RUN echo "host all all 0.0.0.0/0 md5" >> ${PG_HOME}/${PG_VERSION}/main/pg_hba.conf
RUN echo "listen_addresses='*'" >> ${PG_HOME}/${PG_VERSION}/main/postgresql.conf

# Add VOLUMEs to allow backup of config, logs and databases
VOLUME ["${PG_HOME}", "${PG_VAR_LIB}", "${PG_LOGDIR}"]

# Set the default command to run postgreSQL when starting the container
# CMD ["${PG_USR_LIB}/${PG_VERSION}/bin/postgres", "-D", "${PG_VAR_LIB}/${PG_VERSION}/main", "-c", "config_file=${PG_HOME}/${PG_VERSION}/main/postgresql.conf"]

#Configure Java SDK
RUN sed -i 's|#networkaddress.cache.ttl=-1|networkaddress.cache.ttl=10|' /usr/lib/jvm/java-11-openjdk-amd64/jre/lib/security/java.security
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV PATH $PATH:/usr/lib/jvm/java-11-openjdk-amd64/jre/bin:/usr/lib/jvm/java-11-openjdk-amd64/bin
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib
ENV JRE_HOME ${JAVA_HOME}/jre

#Add Usecase-UI server related resources to the docker image
RUN mkdir /home/uui
WORKDIR /home/uui
ADD usecase-ui-server-*-linux64.tar.gz /home/uui/

RUN bash /home/uui/bin/docker-env-config.sh
USER postgres
WORKDIR /home/uui
ENTRYPOINT /home/uui/bin/run.sh
