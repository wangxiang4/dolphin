FROM moxm/java:1.8-full

RUN mkdir -p /dolphin-spring-dashboard

WORKDIR /dolphin-spring-dashboard

ARG JAR_FILE=target/dolphin-spring-dashboard.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8061

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar app.jar $JAVA_OPTS
