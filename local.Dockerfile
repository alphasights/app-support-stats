FROM adoptopenjdk/openjdk15:alpine-jre
RUN apk add --no-cache --update \
    openssl \
    curl \
    bash \
    tini \
    wget \
    tzdata
RUN mkdir -p /app/config
ENV TZ=UTC
RUN cp /usr/share/zoneinfo/UTC /etc/localtime
RUN date > /app/build.date
COPY docker_entrypoint.sh /docker_entrypoint.sh
RUN chmod +x /docker_entrypoint.sh
WORKDIR /app
ENTRYPOINT [ "/sbin/tini", "--", "/docker_entrypoint.sh"]
CMD ["java", "-jar", "/app/app.jar"]