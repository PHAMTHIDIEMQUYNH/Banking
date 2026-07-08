# Sử dụng image Java 17 từ Eclipse Temurin
FROM eclipse-temurin:17-jdk-alpine

# Label thông tin
LABEL maintainer="banking-service"
LABEL version="1.0"
LABEL description="Banking Service with Security"

# Set working directory
WORKDIR /app

# Copy file jar vào container
COPY target/banking-service-0.0.1-SNAPSHOT.jar app.jar

# Expose port (ứng dụng chạy port 8888)
EXPOSE 8888

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]