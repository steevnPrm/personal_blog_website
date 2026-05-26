# --- Étape 1 : Construction ---
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# --- Étape 2 : Exécution ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Redéclaration des arguments pour le stage de runtime
ARG SPRING_DATASOURCE_URL
ARG SPRING_DATASOURCE_USERNAME
ARG SPRING_DATASOURCE_PASSWORD
ARG APP_JWT_SECRET
ARG APP_JWT_EXPIRATION
ARG APP_ADMIN_USERNAME
ARG APP_ADMIN_PASSWORD

# Assignation aux variables d'environnement persistantes
ENV SPRING_DATASOURCE_URL=$SPRING_DATASOURCE_URL
ENV SPRING_DATASOURCE_USERNAME=$SPRING_DATASOURCE_USERNAME
ENV SPRING_DATASOURCE_PASSWORD=$SPRING_DATASOURCE_PASSWORD
ENV APP_JWT_SECRET=$APP_JWT_SECRET
ENV APP_JWT_EXPIRATION=$APP_JWT_EXPIRATION
ENV APP_ADMIN_USERNAME=$APP_ADMIN_USERNAME
ENV APP_ADMIN_PASSWORD=$APP_ADMIN_PASSWORD

COPY --from=build /app/target/*.jar app.jar

# Étape de débogage : permet de vérifier si les variables sont bien injectées lors du build
RUN echo "Vérification des variables :" && env | grep SPRING_

EXPOSE 8080

# Lancement avec arguments --spring pour priorité maximale
ENTRYPOINT ["sh", "-c", "java -jar app.jar \
     --spring.datasource.url=$SPRING_DATASOURCE_URL \
     --spring.datasource.username=$SPRING_DATASOURCE_USERNAME \
     --spring.datasource.password=$SPRING_DATASOURCE_PASSWORD \
     --jwt.secret=$APP_JWT_SECRET \
     --jwt.cookieExpiry=$APP_JWT_EXPIRATION \
     --app.admin.username=$APP_ADMIN_USERNAME \
     --app.admin.password=$APP_ADMIN_PASSWORD"]