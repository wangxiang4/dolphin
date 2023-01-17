FROM moxm/java:1.8-full

RUN mkdir -p /dolphin-monitor

WORKDIR /dolphin-monitor

ARG JAR_FILE=target/dolphin-monitor.jar

COPY ${JAR_FILE} app.jar

EXPOSE 8061

ENV TZ=Asia/Shanghai JAVA_OPTS="-Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

CMD sleep 60; java -jar app.jar $JAVA_OPTS
