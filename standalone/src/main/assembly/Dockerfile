FROM nexus3.onap.org:10001/onap/integration-java17:12.0.0
MAINTAINER "Lu Ji" <lu.ji3@zte.com.cn>

# Expose the Usecase-UI backend port 
EXPOSE 8082

USER root
RUN apk update && apk add bash
#Configure Java SDK
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib

#Add Usecase-UI server related resources to the docker image
RUN mkdir /home/uui
WORKDIR /home/uui
ADD usecase-ui-server-*-linux64.tar.gz /home/uui/
RUN addgroup -S uui && adduser -S uui -G uui && chown -R uui:uui /home/uui

USER uui
WORKDIR /home/uui
ENTRYPOINT /home/uui/bin/run.sh