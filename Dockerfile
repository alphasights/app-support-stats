FROM gradle:jdk15 as builder
ARG GITHUB_REGISTRY_TOKEN
USER root
COPY ./.git /app/.git
COPY ./src /app/src
COPY ./build.gradle.kts settings.gradle.kts /app/
RUN cd /app; echo "$(git rev-parse --short HEAD)" > /app/git-commit.hash
RUN cd /app; sed -i "s/v0.0.x/$(git rev-parse --short HEAD)/g" src/main/resources/static/index.html && \
    cat src/main/resources/static/index.html
RUN ./gradlew -p /app clean bootJar

FROM adoptopenjdk/openjdk15:alpine-jre
COPY --from=builder /app/build/libs/*.jar /app/app.jar
RUN apk add --no-cache --update \
    openssl \
    curl \
    bash \
    tini \
    wget \
    tzdata
RUN wget -O /app/dd-java-agent.jar 'https://repository.sonatype.org/service/local/artifact/maven/redirect?r=central-proxy&g=com.datadoghq&a=dd-java-agent&v=LATEST'
ENV TZ=UTC
RUN cp /usr/share/zoneinfo/UTC /etc/localtime
RUN date > /app/build.date
COPY docker_entrypoint.sh /docker_entrypoint.sh
RUN chmod +x /docker_entrypoint.sh
WORKDIR /app
ENTRYPOINT [ "/sbin/tini", "--", "/docker_entrypoint.sh"]
CMD ["java", "-jar", "/app/app.jar"]