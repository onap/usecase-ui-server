FROM ubuntu:16.04

MAINTAINER "Lu Ji" <lu.ji3@zte.com.cn>

EXPOSE 8082
EXPOSE 3306

#install openjdk-1.8
#RUN sed -i 's#http://archive.ubuntu.com#http://mirrors.163.com#g' /etc/apt/sources.list

RUN apt-get update && \
    apt-get install -y openjdk-8-jdk && \
    apt-get install -y gcc && \
    apt-get install -y libmysqlclient-dev && \
    apt-get install -y mysql-server && \
    apt-get install -y mysql-client && \
    apt-get install -y curl && \
    apt-get install -y build-essential && \
    apt-get install -y libssl-dev && \
    apt-get install -y libffi-dev

#configure the JDK
RUN sed -i 's|#networkaddress.cache.ttl=-1|networkaddress.cache.ttl=10|' /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security/java.security
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $PATH:/usr/lib/jvm/java-8-openjdk-amd64/jre/bin:/usr/lib/jvm/java-8-openjdk-amd64/bin
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib
ENV JRE_HOME ${JAVA_HOME}/jre

#add uui related resources to the docker image
RUN mkdir /home/uui
WORKDIR /home/uui
ADD usecase-ui-server-*-linux64.tar.gz /home/uui/
RUN chmod 755 /home/uui/bin/*.sh

ENTRYPOINT /home/uui/bin/run.sh
