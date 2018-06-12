FROM openjdk:9-jre-slim
ENTRYPOINT ["java","-XX:+UnlockExperimentalVMOptions","-XX:+UseCGroupMemoryLimitForHeap","-XX:MaxRAMFraction=1","-jar","/opt/vertx-java-fat.jar","-cluster","-cluster-port", "15701"]
ARG JAR_FILE
ADD target/${JAR_FILE} /opt/vertx-java-fat.jar
