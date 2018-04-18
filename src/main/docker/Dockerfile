FROM java
#VOLUME /tmp
ADD nhb-platform-3.0.1.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]