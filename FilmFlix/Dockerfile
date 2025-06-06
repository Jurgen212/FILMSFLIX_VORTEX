FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copiar archivos de configuración Maven
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fuente y construir
COPY src ./src

# Copiar credenciales de Firebase si existen
ARG FIREBASE_CREDENTIALS_PATH=firebasekeys.json
COPY ${FIREBASE_CREDENTIALS_PATH} ./${FIREBASE_CREDENTIALS_PATH}

# Construir la aplicación
RUN mvn package -DskipTests -B

# Etapa de ejecución
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Instalar herramientas necesarias
RUN apk add --no-cache netcat-openbsd curl

# Copiar archivos construidos
COPY --from=build /app/target/*.jar app.jar

# Copiar credenciales de Firebase si existen
ARG FIREBASE_CREDENTIALS_PATH=firebasekeys.json
COPY --from=build /app/${FIREBASE_CREDENTIALS_PATH} ./${FIREBASE_CREDENTIALS_PATH}

# Copiar script de entrada
COPY entrypoint.sh ./entrypoint.sh
RUN chmod +x ./entrypoint.sh

# Crear usuario no root para seguridad
RUN addgroup -g 1001 -S filmflix && \
    adduser -S filmflix -u 1001 -G filmflix
USER filmflix

EXPOSE 8080

HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

ENTRYPOINT ["./entrypoint.sh"]