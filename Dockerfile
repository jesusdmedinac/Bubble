FROM openjdk:17-jdk-slim
ARG PORT
ARG GEMINI_KEY
ARG FIREBASE_DATABASE_NAME
ENV PORT=$PORT
ENV GEMINI_KEY=$GEMINI_KEY
ENV FIREBASE_DATABASE_NAME=$FIREBASE_DATABASE_NAME
RUN mkdir /server
COPY ./server/build/libs/*-all.jar /server/server-bubble.jar
ENTRYPOINT ["java","-jar","/server/server-bubble.jar"]