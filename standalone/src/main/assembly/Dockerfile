FROM nexus3.onap.org:10001/onap/integration-java11:9.0.0

EXPOSE 8082

#Configure Java SDK
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib

USER root
RUN  apk update && apk add bash
#Add Usecase-UI server related resources to the docker image

WORKDIR /home/uui
ENTRYPOINT ["/bin/sh","-c","/home/uui/bin/run.sh"]
