//Encoded with - 'Encode in ANSI' in Notepad++
pipeline {

   agent any
   
   tools {
     maven 'Jenkins-Maven'
   }
  
   //Here we are assuming that the maven build and docker build are happening in the same server where Jenkins is installed
   parameters {
     choice(name: 'buildEnvironment', choices: ['default', 'dev', 'sit', 'uat', 'pt', 'prod'], description: 'Choose an environment for build server.')
   }
   
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     BUILD_ENV = "${params.buildEnvironment}"  
     
     DOCKER_REGISTRY_URL = 'https://registry.hub.docker.com/'
     DOCKER_REGISTRY_CREDENTIALS = 'global-docker-registry-credentials'
     DOCKER_REGISTRY_IMAGE = "msiddiq123/country-curd-rest-service:img-${env.BUILD_ID}" 
     
     NEXUS_REGISTRY_URL = 'http://192.168.1.35:9191/'
     NEXUS_REGISTRY_CREDENTIALS = 'global-nexus-registry-credentials'
     //NEXUS_REGISTRY_IMAGE = "192.168.1.35:9191/country-curd-rest-service:img-${env.BUILD_ID}"
     NEXUS_REGISTRY_IMAGE = "192.168.1.35:9191/country-curd-rest-service:img-44"
     
   }
   
   //retry(2) 
   // https://www.jenkins.io/doc/book/pipeline/syntax/#options
   options {     
     timestamps()
     timeout(time: 15, unit: 'MINUTES')   
     buildDiscarder(logRotator(numToKeepStr: '15'))     
   }
  

   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' and in windows we can use bat like bat 'mvn -version'
   stages {
   
     stage('Prepare Build Job') {
	steps {
	  echo '#################################################### Executing stage - Prepare Build Job ####################################################'
	  echo 'Reading Jenkinsfile...'
	  //bat 'type Jenkinsfile'
          //echo "M2_HOME ====> ${M2_HOME}"
	  //echo "PATH ====> ${PATH}"
	  echo 'Checking maven version...'
          bat 'mvn -version' 
	  echo 'Checking docker version...'
	  bat 'docker -v'
	  shell('''#!/bin/bash -e
                echo "Hello from bash"
                echo "Who I'm $SHELL"
          ''')	
	  
        }//steps
     }//stage
     
     stage('Build Project') {
	when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature' || env.BRANCH_NAME == 'release' 
	   }
        }
	steps {
	  echo '#################################################### Executing stage - Build Project ####################################################'        
	  bat 'mvn clean install -Dmaven.test.skip=true'
          bat 'dir /p'	  
        }//steps
     }//stage
     
     stage('Build Docker Image') { 
        when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature' || env.BRANCH_NAME == 'release' 
	   }
        }     
	steps {
	  echo '#################################################### Executing stage - Build Docker Image ####################################################'
          echo 'Reading Dockerfile...'
	  bat 'type Dockerfile'	  
	  //Using Jenkins Credentials only for a particular stage
	  withCredentials([
	      usernamePassword(credentialsId: 'global-jenkins-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')
	  ]){
		//https://wiki.jenkins.io/display/JENKINS/Credentials%20Binding%20Plugin
		echo "Connecting to Jenkins Server with ${USERNAME} and ${PASSWORD}"
	  }
	  
	  //script{
	     //Ensure that docker(or docker swarm is configured) engine is installed in the Jenkins server and the Docker service is running.
	     //https://www.jenkins.io/doc/book/pipeline/docker/
	     //docker.withRegistry(DOCKER_REGISTRY_URL, DOCKER_REGISTRY_CREDENTIALS) {
                //def customImage = docker.build(DOCKER_REGISTRY_IMAGE)               
                //customImage.push()
             //}
	       //docker.withRegistry(NEXUS_REGISTRY_URL, NEXUS_REGISTRY_CREDENTIALS) {
                 //def customImage = docker.build(NEXUS_REGISTRY_IMAGE)               
                 //customImage.push()
               //}	     
	  //}//script
	  
	  //bat 'docker images -a'
	  bat "docker image ls ${NEXUS_REGISTRY_IMAGE}"

	  //FINDSTR 'id' is the equivalent of grep in Linux
          //bat 'docker rmi ${NEXUS_REGISTRY_IMAGE}'
	  //bat "docker pull ${NEXUS_REGISTRY_IMAGE}"
	  //bat "docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 ${NEXUS_REGISTRY_IMAGE}"
        }//steps
     }//stage 
     
   }//stages
   
   post {
     always {
	echo '#################################################### Executing post [always] handler ####################################################'
        echo 'Job execution completed...'		
     }
     
     //https://plugins.jenkins.io/email-ext/
     success {
        echo '#################################################### Executing post [success] handler ####################################################'        
	emailext attachLog: true,
	compressLog: true,
	to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
	mimeType: 'text/plain',
        body: "Hi Team, \n\n Please find the build and console log details below:- \n Job Name >> ${env.JOB_NAME} \n Build No. >> ${env.BUILD_NUMBER} \n GIT Branch >> ${env.BRANCH_NAME} \n Build Environment >> ${BUILD_ENV} \n Build Status >> ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"     
     }
     
     //https://plugins.jenkins.io/email-ext/
     failure {
       echo '#################################################### Executing post [failure] handler ####################################################'
       //mail to: 'maroof.siddique2013@gmail.com',
       //subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${currentBuild.result} !",
       //body: "Please find the build and console log details at ${env.BUILD_URL}" 
       emailext attachLog: true,
       compressLog: true,
       to: 'maroof.siddique2013@gmail.com',
       subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
       mimeType: 'text/plain',
       body: "Hi Team, \n\n Please find the build and console log details below:- \n Job Name >> ${env.JOB_NAME} \n Build No. >> ${env.BUILD_NUMBER} \n GIT Branch >> ${env.BRANCH_NAME} \n Build Environment >> ${BUILD_ENV} \n Build Status >> ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"
     }  
   }//post
      
}//pipeline