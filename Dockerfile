# Verwende ein offizielles Java-Grundbild als Basis
FROM openjdk:17

# Setze das Arbeitsverzeichnis innerhalb des Containers
WORKDIR /app

# Kopiere die kompilierte JAR-Datei deiner Anwendung in das Arbeitsverzeichnis
COPY build/libs/pocService-0.0.1-SNAPSHOT.jar app.jar

# Befehl, um deine Spring Boot-Anwendung auszuführen
CMD ["java", "-jar", "app.jar"]

