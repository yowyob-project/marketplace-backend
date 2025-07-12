# Utilise une image Java légère avec JDK 17 (compatible Spring Boot 3+)
FROM openjdk:21-jdk-slim

# Crée un répertoire de travail dans le conteneur
WORKDIR /app

# Copie le JAR dans le conteneur
COPY target/marketplace-service-0.0.1-SNAPSHOT.jar app.jar

# Déclare les ports que ton application va utiliser
EXPOSE 4010
# Ajoute d'autres ports ici si besoin, exemple : EXPOSE 8081 9090

# Commande de lancement
ENTRYPOINT ["java", "-jar", "app.jar"]
