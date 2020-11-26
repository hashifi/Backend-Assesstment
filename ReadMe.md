# Getting Started

### Guides
How to build and run the project with docker.
1. Excute below command to build project with maven. <br/>
```./mvnw clean install -Dmaven.test.skip=true```
1. Create the Docker Image for the application.<br/>
```docker build -t rhb-backend-assestment```
1. Then pull the MySQL from the Docker Hub.<br/>
```docker pull mysql:8.0.18```
1. Run MySQL as a container. <br/>
```docker run --name docker-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=RHBASSESSTMENT -e MYSQL_USER=app -e MYSQL_PASSWORD=Password123 -d mysql:8.0.18```
1. Final step, run the Sprint Boot application. <br/>
```docker run -d -p 8080:8080 --name rhb-backend-assestment --link docker-mysql:mysql rhb-backend-assestment```