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
             //Use bat for windows and sh for Linux hosts/nodes 
            //https://www.robvanderwoude.com/escapechars.php
	    bat '''
	       @echo off
	       echo '################################## Stage - Initialize ##################################'
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
	    bat '''
	      @echo off
	      echo '################################## Stage - Maven Build ##################################' 
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
	     bat '''
	        @echo off
		echo '################################## Stage - Docker Build ##################################'
	        echo 'Building docker image on Docker Non-Prod Server ==========' %DOCKER_NON_PROD_SERVER%
	     '''
	     script{
		  //https://www.jenkins.io/doc/book/pipeline/docker/
	  	  docker.withRegistry(DOCKER_NON_PROD_REGISTRY_URL, DOCKER_NON_PROD_REGISTRY_CREDENTIALS) {
		    def customImage = docker.build(DOCKER_REGISTRY_IMAGE)               
		    customImage.push()
		  }
	     }

	     bat '''
	        @echo off		
	        docker image ls -a
		//docker image ls %DOCKER_REGISTRY_IMAGE%
	        docker rmi %DOCKER_REGISTRY_IMAGE%
		docker rmi registry.hub.docker.com/%DOCKER_REGISTRY_IMAGE%
	     '''
        }//steps
     }//stage

     stage('Docker Deploy') { 
        when { 
           allOf{
		  expression { 		    
		     env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*' 
		  }
		  expression {
                     params.BuildEnvironment == 'default' || params.BuildEnvironment == 'dev' || params.BuildEnvironment == 'sit' || params.BuildEnvironment == 'uat' || params.BuildEnvironment == 'pt'                       		       
                  }
		  expression {
                     params.SkipDeploy == false
                  }
           } 	              
        }     
	steps {
	     bat '''
	        @echo off
		echo '################################## Stage - Docker Deploy ##################################'
	        echo 'Deploying docker image on Docker Non-Prod Server ==========' %DOCKER_NON_PROD_SERVER%		
		docker pull %DOCKER_REGISTRY_IMAGE%
	        docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 %DOCKER_REGISTRY_IMAGE%	  
		docker ps -a
	        
	     '''
        }//steps //docker ps -aqf ancestor=%DOCKER_REGISTRY_IMAGE%
     }//stage      
   }//stages
   
   post {
     always {
	echo '################################## Executing post [always] handler ##################################'       
        emailext attachLog: true,
	to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
	mimeType: 'text/plain',
        body: "Hi Team, \n\n Please find the build and console log details below:- \n\n Job Name :: ${env.JOB_NAME} \n GIT Branch :: ${env.BRANCH_NAME} \n GIT Commit ID :: ${env.GIT_COMMIT} \n Project GroupId :: ${PROJECT_GROUP_ID} \n Project ArtifactID :: ${PROJECT_ARTIFACT_ID} \n Project Version :: ${PROJECT_VERSION} \n Project Packaging :: ${PROJECT_PACKAGING} \n Build No. :: ${env.BUILD_NUMBER} \n Build Environment :: ${BUILD_ENV} \n Build Status :: ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"     	
     }
   }//post   
}//pipeline