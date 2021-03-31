//Assumptions:-
//a)Encoded with - 'Encode in ANSI' in Notepad++
//b)Here we are assuming that the maven build and docker build are happening in the same box where Jenkins server is installed
//c)We are creating a container with replica=1
//d)For 'country-curd-rest-service-job' mutibranch pipeline job, it creates a folder with this name in jenkins dashboard and creates child jobs inside this folder for each of the branches...so you can only configure the parent job and not the child jobs


pipeline {

   agent any
   
   tools {
     maven 'Jenkins-Maven'
   }
    
   parameters {         
     choice(name: 'BuildEnvironment', choices: ['default', 'dev', 'sit', 'uat', 'pt', 'prod'], description: 'Choose an environment for build server.\n NOTE:- Run commands like - docker rmi <image-id>, docker stop <container-id> and docker rmi <container-id> before triggering the build.')     
     booleanParam(name: 'SkipJunit', defaultValue: true, description: 'This flag will check if the junit test will run or skip')
     booleanParam(name: 'SkipDeploy', defaultValue: false, description: 'This flag will check if the job will proceed with deployment')
   }
   
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     BUILD_ENV = "${params.BuildEnvironment}"
     SKIP_JUNIT = "${params.SkipJunit}" 
     SKIP_DEPLOY = "${params.Deploy}"     
     
     pom = readMavenPom(file: 'pom.xml')
     PROJECT_GROUP_ID = pom.getGroupId()
     PROJECT_ARTIFACT_ID = pom.getArtifactId()
     PROJECT_VERSION = pom.getVersion()
     PROJECT_PACKAGING = pom.getPackaging()
     
     DOCKER_NON_PROD_SERVER = "192.168.1.35"
     DOCKER_NON_PROD_REGISTRY_URL = 'https://registry.hub.docker.com/'
     DOCKER_NON_PROD_REGISTRY_CREDENTIALS = 'global-docker-registry-credentials'
     DOCKER_IMAGE_NAME = "msiddiq123/country-curd-rest-service"
     //DOCKER_IMAGE_TAG = "img-${env.BUILD_ID}"
     DOCKER_IMAGE_TAG = "${env.BRANCH_NAME}_${PROJECT_VERSION}"
     DOCKER_REGISTRY_IMAGE = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"           
   }
   
   options {     
     timestamps()
     timeout(time: 15, unit: 'MINUTES')   
     buildDiscarder(logRotator(numToKeepStr: '15'))  
   }
     
   stages {   
     stage('Initialize') {
	steps { 
	  echo '################################## Stage - Initialize ##################################'
          //Use bat for windows and sh for Linux hosts/nodes 
         //https://www.robvanderwoude.com/escapechars.php
	  bat '''
	     @echo off
	     echo 'M2_HOME ==========' %M2_HOME%
	     echo 'PATH ==========' %PATH%
	     mvn -version
	     docker -v
	  '''   
        }//steps
     }//stage
     
     stage('Maven Build') {
        when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*' 
	   }
        }
	steps { 
	  echo '################################## Stage - Maven Build ##################################'        
	  bat '''
	     @echo off
	     echo 'Git Branch ==========' %BRANCH_NAME%
             echo 'Packaging for ==========' %PROJECT_GROUP_ID%:%PROJECT_ARTIFACT_ID%:%PROJECT_VERSION%:%PROJECT_PACKAGING%
	     echo 'Skip Junit Test ==========' %SKIP_JUNIT%
             mvn clean install -Dmaven.test.skip=%SKIP_JUNIT%	     
	  '''   
        }//steps
     }//stage
     
     stage('Docker Build') { 
        when { 
           allOf{
		  expression { 		    
		     env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*' 
		  }
		  expression {
                     params.BuildEnvironment == 'default' || params.BuildEnvironment == 'dev' || params.BuildEnvironment == 'sit' || params.BuildEnvironment == 'uat' || params.BuildEnvironment == 'pt'                       		       
                  }
           } 	              
        }     
	steps {
	  echo '################################## Stage - Docker Build ##################################'
	  script{
	     bat '''
	        @echo off
	        echo 'Building docker image on Docker Non-Prod Server ==========' %DOCKER_NON_PROD_SERVER%
	        //type Dockerfile
		docker login -u msiddiq123 -p Msiddiq@123 https://index.docker.io/v1
		docker build -t %DOCKER_REGISTRY_IMAGE% .
		docker push %DOCKER_REGISTRY_IMAGE%
	     '''
	     
	     //https://www.jenkins.io/doc/book/pipeline/docker/
	     //docker.withRegistry(DOCKER_NON_PROD_REGISTRY_URL, DOCKER_NON_PROD_REGISTRY_CREDENTIALS) {
                //def customImage = docker.build(DOCKER_REGISTRY_IMAGE)               
                //customImage.push()
             //}	
	     
	     bat '''
	        @echo off
	        docker image ls %DOCKER_REGISTRY_IMAGE%
	        docker rmi %DOCKER_REGISTRY_IMAGE% 
	     '''
	  }//script 
        }//steps
     }//stage 
   }//stages
          
}//pipeline