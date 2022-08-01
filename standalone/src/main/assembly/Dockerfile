FROM nexus3.onap.org:10001/onap/integration-java11:9.0.0

EXPOSE 8082


#Configure Java SDK
ENV CLASSPATH .:${JAVA_HOME}/lib:${JRE_HOME}/lib


USER root

#Add Usecase-UI server related resources to the docker image
RUN   chmod -R 0700 /home/uui


WORKDIR /home/uui

ENTRYPOINT ["sh", "-c"]
CMD ["exec /home/uui/bin/run.sh"]
