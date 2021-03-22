#############################################################################################################################################################
	#1)build the project using mvn clean install -Dmaven.test.skip=true
	#2)Docker Hub Login/Logout - docker system info / docker login / docker logout / docker login -u msiddiq123 -p **** https://index.docker.io/v1  
	#                          - NB:-Your email and your docker id are 2 separate things. It looks like you can use your email to log in the site but not the CLI. 
	#3)Docker Build Image - docker build -t msiddiq123/country-curd-rest-service:dev-1.0 .
	#4)Docker List Images - docker image ls  OR docker images -a
	#5)Docker Hub Push - docker push msiddiq123/country-curd-rest-service:dev-1.0
	#6)Docker Image Delete (from Local) - docker rmi msiddiq123/country-curd-rest-service:dev-1.0
	#7)Docker Hub Pull - docker pull msiddiq123/country-curd-rest-service:dev-1.0
	#8)Docker Run Image - docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 msiddiq123/country-curd-rest-service:dev-1.0
	#9)Docker Container Status - docker ps -a
	#10)Docker Container Login - docker exec -it bb619399082d /bin/bash
	#11)Docker Conatiner Resart - docker restart --time=10 bb619399082d
	#12)Docker Container Stop - docker stop bb619399082d
	#13)Docker Conatiner Delete - docker rm bb619399082d 
	#14)Docker Image Delete - docker rmi msiddiq123/country-curd-rest-service:dev-1.0
############################################################################################################################################################

#FROM openjdk:11-jre-slim
#FROM openjdk:8-jdk-alpine
FROM openjdk:8

LABEL version="1.0"

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en

RUN echo pwd
#RUN sh -c 'echo pwd'
RUN ls -al
#RUN sh -c 'ls -al'

COPY ./target/CountryCurdRestService-0.0.1-SNAPSHOT.jar /opt

WORKDIR /opt
EXPOSE 8081

#CMD ["./CountryCurdRestService.sh"]
cmd ["sh" ,"-c","Executing Docker Image..."]
cmd ["java", "-jar","/opt/CountryCurdRestService-0.0.1-SNAPSHOT.jar"]

