FROM openjdk:11.0.9.1-jre-slim
EXPOSE 8080
VOLUME /tmp
ADD target/*.jar app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]