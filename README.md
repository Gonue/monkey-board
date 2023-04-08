# Simple Board

## 🖥️ Develop Enviroment
<p>
<img src="https://img.shields.io/badge/Java 11-1E8CBE?style=for-the-badge&logo=&logoColor=white"> 
<img src="https://img.shields.io/badge/springboot 2.7-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/Gradle 7.6-02303A?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/Intellij IDEA Ultimate-000000?style=for-the-badge&logo=Intellij IDEA&logoColor=white">
</p>

----

## 📂 Detail Stack
<p>
<img src="https://img.shields.io/badge/JAVA 11-3776AB?style=for-the-badge&logo=Java&logoColor=white">
<img src="https://img.shields.io/badge/Spring DATA JPA-6DB33F?style=for-the-badge&logo=Codeforces&logoColor=white">
<img src="https://img.shields.io/badge/Spring DATA REST-6DB33F?style=for-the-badge&logo=Databricks&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white">
</p>
<p>
<img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=Postgresql&logoColor=white">
</p>


<p>
<img src="https://img.shields.io/badge/PostMan-FF6C37?style=for-the-badge&logo=Postman&logoColor=white">
<img src="https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=JUnit5&logoColor=white">
</p>

<p>
<img src="https://img.shields.io/badge/docker-2496ED?style=for-the-badge&logo=docker&logoColor=white">
<img src="https://img.shields.io/badge/AWS ECR-FF9900?style=for-the-badge&logo=Amazon EKS&logoColor=white">
<img src="https://img.shields.io/badge/AWS ECS-FF9900?style=for-the-badge&logo=Amazon ECS&logoColor=white">
</p>

<p>
<img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/gitkraken-179287?style=for-the-badge&logo=gitkraken&logoColor=white">
</p>

----

## [🔗 ERD](https://github.com/Gonue/simple-board/tree/master/docs)

----

## Getting Docker Start


## 🐳 docker-compose

### docker-compose.yml
```yaml
version: "3.8"

services:

  board-test-postgresql:
    container_name: board-test-postgres
    image: postgres
    ports:
      - "5432:5432"
    environment:
      POSTGRES_DB: board
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: 1234
    volumes:
      - /var/lib/postgresql/data
    networks:
      - board-network

  gonue-board-server:
    container_name: gonue-board-server
    build: .
    depends_on:
      - board-test-postgresql
    image: gonue/board-server
    ports:
      - "8080:8080"
    networks:
      - board-network
    restart: always
networks:
  board-network:
    driver: bridge

```

```shell
$ docker-compose up
```

----
## 📑 Reference
### [🔗 Docker Hub](https://hub.docker.com/repository/docker/gonue/board-server/general)
### [🔗 Packages Link](https://github.com/Gonue/simple-board/pkgs/container/board-server)
### [🔗 Demo Page]()

----

