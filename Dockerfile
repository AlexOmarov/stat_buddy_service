FROM bellsoft/liberica-openjdk-alpine:17
RUN addgroup -S docuser && adduser -S docuser -G docuser
RUN mkdir -p /var/dumps

USER docuser:docuser

VOLUME /tmp
ARG DEPENDENCY=stat_buddy_app/build/install
COPY ${DEPENDENCY}/stat_buddy_app-boot/lib /app/lib

EXPOSE 8080
ENTRYPOINT ["java","-cp","app:app/lib/*", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/var/dumps", "-jar", "/app/lib/stat_buddy_app.jar"]
