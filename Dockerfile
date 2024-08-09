FROM openjdk:21 as builder
WORKDIR /app
COPY . /app
RUN microdnf install findutils
RUN ./gradlew --no-build-cache --no-daemon bootJar
RUN java -Djarmode=tools -jar build/libs/k8s-demo-project.jar extract --layers --launcher --destination /extract/

FROM openjdk:21
WORKDIR app
COPY --from=builder /extract/dependencies/ ./
COPY --from=builder /extract/spring-boot-loader/ ./
COPY --from=builder /extract/snapshot-dependencies/ ./
COPY --from=builder /extract/application/ ./
CMD java org.springframework.boot.loader.launch.JarLauncher
