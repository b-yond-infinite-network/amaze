# Getting Started

#### I write this challenge using java and based on Spring/Spring boot Framework. I use keycloak for authentication. You can run it using `docker-compose up`. The docker-compose has three phases to run this application:
1. pull postgres database, keycloak and this application
2. run postgres
3. run keycloak
4. run the API

### Run Instruction using docker
- 'docker-compose up'

###### Note:
1- I can't import/export keycloak configuration because I face some issues when the docker-compose launch the application,
so please after a success running you need to login to the keycloak UI `http://localhost:8080/auth/` and create a new client-id,
roles (employee, manager) and users, or you can disable the authentication mechanism under 'src/main/resources/docker.yaml' 
(but you need to build new image using 'docker build -t alidandach/challenge1:latest .')

2- The running containers reserve the following ports : 5432 (postgres), 8080(keycloak), 8088 (API) and you can change it from `docker-compose.yml`

### Run Instruction without docker 
- Make sure you have java 11
- create a new db named 'shifts-service-db' on your postgresql server (You can edit `src/main/resources/application.yaml` if you want to change the db name)
- I have disabled the authentication mechanism under `src/main/resources/application.yaml`
- At the runtime the API create a new schema and tables
- You can build a new jar file using maven `./mvn package`
- Run it using java `java -jar target/bus-drivers-shifts-service.jar`


### Overall:
- You have the postman collection `Challenge 1.postman_collection.json`
- Also, I put swagger
- I write test case