//Assumptions:-
//a)Encoded with - 'Encode in ANSI' in Notepad++
//b)Here we are assuming that the maven build and docker build are happening in the same box where Jenkins server is installed
//c)https://www.jenkins.io/doc/book/pipeline/

def gvy

pipeline {

   agent any
   
   tools {
     maven 'Jenkins-Maven'
   }
    
   parameters {
     choice(name: 'buildEnvironment', choices: ['default', 'dev', 'sit', 'uat', 'pt', 'prod'], description: 'Choose an environment for build server.\n NOTE:- Run docker stop <container-id> and docker rmi <container-id> before triggering the build.')     
     booleanParam(name: 'deployFlag', defaultValue: true, description: 'This flag will determine if a docker image should be deployed')
   }
   
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     BUILD_ENV = "${params.buildEnvironment}"  
     
     pom = readMavenPom(file: 'pom.xml')
     PROJECT_GROUP_ID = pom.getGroupId()
     PROJECT_ARTIFACT_ID = pom.getArtifactId()
     PROJECT_VERSION = pom.getVersion()
     PROJECT_PACKAGING = pom.getPackaging()
     
     DOCKER_REGISTRY_URL = 'https://registry.hub.docker.com/'
     DOCKER_REGISTRY_CREDENTIALS = 'global-docker-registry-credentials'
     DOCKER_IMAGE_NAME = "msiddiq123/country-curd-rest-service"
     //DOCKER_IMAGE_TAG = "img-${env.BUILD_ID}"
     DOCKER_IMAGE_TAG = "img-${PROJECT_VERSION}"
     DOCKER_REGISTRY_IMAGE = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}" 
     
     NEXUS_REGISTRY_URL = 'http://192.168.1.35:9191/'
     NEXUS_REGISTRY_CREDENTIALS = 'global-nexus-registry-credentials'
     NEXUS_IMAGE_NAME = "192.168.1.35:9191/country-curd-rest-service"     
     //NEXUS_IMAGE_TAG = "img-${env.BUILD_ID}"
     NEXUS_IMAGE_TAG = "img-${PROJECT_VERSION}"
     NEXUS_REGISTRY_IMAGE = "${NEXUS_IMAGE_NAME}:${NEXUS_IMAGE_TAG}"     
   }
   
   options {     
     timestamps()
     timeout(time: 15, unit: 'MINUTES')   
     buildDiscarder(logRotator(numToKeepStr: '15'))
     //retry(2)      
   }
     
   stages {
   
     stage('Prepare Build Job') {
	steps { 
	   echo '################################## Executing stage - Prepare Build Job ##################################'
	  
	  //echo 'Reading Jenkinsfile...'
	  //bat 'type Jenkinsfile'
          echo "M2_HOME ====> ${M2_HOME}"
	  echo "PATH ====> ${PATH}"
	  echo 'Checking maven version...'
          bat 'mvn -version' 
	  echo 'Checking docker version...'
	  bat 'docker -v'
          script {
             gv = load "groovy_script.groovy" 
          }
	  

	  //https://stackoverflow.com/questions/35043665/change-windows-shell-in-jenkins-from-cygwin-to-git-bash-msys#:~:text=Go%20to%20Manage%20Jenkins%20%3E%20Configure,the%20Execute%20shell%20build%20step.&text=Note%3A%20This%20won't%20work,agents%20(JENKINS%2D38211).
	  //-----For Linux----
	  //shell('''#!/bin/bash
                //set -e
		//set -x
		//echo "Hello from bash"
                //echo "Who I'm $SHELL"
         //''')
	 //-----OR----
	 //https://www.jenkins.io/doc/pipeline/tour/running-multiple-steps/
	 //sh 'echo "Hello World"'
         //sh '''
               //echo "Multiline shell steps works too"
               //ls -lah
          //'''
	  //-----For Windows----
	  //bat ''' 
	     //echo "multiline bat script start >>>>>>>>>>>"
	     //dir /p
	     //set appecho="This is testing for echo"
	     //echo %appecho%
	     //echo "multiline bat script end >>>>>>>>>>>"	     
	  //'''	  
        }//steps
     }//stage
     
     stage('Build Project') {
	when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature' || env.BRANCH_NAME == 'release' 
	   }
        }
	steps {
	  script {
            gv.buildProj()
          }
	  echo '################################## Executing stage - Build Project ##################################' 
          echo "Buildng ====> ${PROJECT_GROUP_ID}:${PROJECT_ARTIFACT_ID}:${PROJECT_VERSION}:${PROJECT_PACKAGING}" 	  
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
	  script {
            gv.buildImage()
          }
	  echo '################################## Executing stage - Build Docker Image ##################################'
          //echo 'Reading Dockerfile...'
	  //bat 'type Dockerfile'	  
	  
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
	  
	  
	  //bat "docker images -a"
	  //bat "docker image ls ${NEXUS_REGISTRY_IMAGE}"
	  //bat "docker rmi ${NEXUS_REGISTRY_IMAGE}" 
        }//steps
     }//stage 
     
     stage('Deploy Docker Image') { 
        when {
             //anyof
	     allOf{
		expression { 
		  env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature' || env.BRANCH_NAME == 'release' 
	        }
	        expression {
                  params.deployFlag
                }
             }           
        }     
	steps {
          script {
            gv.deployImage()
          }	
	  echo '################################## Executing stage - Deploy Docker Image ##################################'
	  //bat "docker pull ${NEXUS_REGISTRY_IMAGE}"
	  //bat "docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 ${NEXUS_REGISTRY_IMAGE}"	
          //bat "docker ps -a"	  
	  //bat "docker ps -aqf ancestor=${NEXUS_REGISTRY_IMAGE}"	  
        }//steps
     }//stage 
     
   }//stages
   
   post {
     //https://plugins.jenkins.io/email-ext/
     always {
	echo '################################## Executing post [always] handler ##################################'
       
        emailext attachLog: true,
	compressLog: true,
	to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
	mimeType: 'text/plain',
        body: "Hi Team, \n\n Please find the build and console log details below:- \n\n Job Name :: ${env.JOB_NAME} \n GIT Branch :: ${env.BRANCH_NAME} \n Project GroupId :: ${PROJECT_GROUP_ID} \n Project ArtifactID :: ${PROJECT_ARTIFACT_ID} \n Project Version :: ${PROJECT_VERSION} \n Project Packaging :: ${PROJECT_PACKAGING} \n Build No. :: ${env.BUILD_NUMBER} \n Build Environment :: ${BUILD_ENV} \n Build Status :: ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"     	
     }
     
     success {
        echo '################################## Executing post [success] handler ##################################'        
	echo 'Job execution succeded...'
     }
     
     failure {
       echo '################################## Executing post [failure] handler ##################################'
       echo 'Job execution failed...'
     }  
   }//post
      
}//pipeline