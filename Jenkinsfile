//Assumptions:-
//a)Encoded with - 'Encode in ANSI' in Notepad++
//b)Here we are assuming that the maven build and docker build are happening in the same box where Jenkins server is installed
//c)We are creating a container with replica=1
//d)For 'country-curd-rest-service-job' mutibranch pipeline job, it creates a folder with this name in jenkins dashboard and creates child jobs inside this folder for each of the branches...so you can only configure the parent job and not the child jobs
//Reference:-
//a)https://www.jenkins.io/doc/book/pipeline/
//b)https://www.youtube.com/watch?v=B_2FXWI6CWg


pipeline {

   agent any
   
    //triggers {
      //Will run at every 30 minutes (may be at XX:01,XX:31 ..)
      //cron('*/30 * * * *')
    //}
   
   tools {
     maven 'Jenkins-Maven'
   }
    
   parameters {         
     choice(name: 'BuildEnvironment', choices: ['default', 'dev', 'sit', 'uat', 'pt', 'prod'], description: 'Choose an environment for build server.\n NOTE:- Run commands like - docker rmi <image-id>, docker stop <container-id> and docker rmi <container-id> before triggering the build.')     
     booleanParam(name: 'CheckDeploy', defaultValue: true, description: 'This flag will check if the job will proceed with deployment')
   }
   
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     BUILD_ENV = "${params.BuildEnvironment}"  
     
     pom = readMavenPom(file: 'pom.xml')
     PROJECT_GROUP_ID = pom.getGroupId()
     PROJECT_ARTIFACT_ID = pom.getArtifactId()
     PROJECT_VERSION = pom.getVersion()
     PROJECT_PACKAGING = pom.getPackaging()
     
     DOCKER_NON_PROD_SERVER = "192.168.1.35"
     DOCKER_PROD_SERVER = "192.168.1.36"
     DOCKER_NON_PROD_REGISTRY_URL = 'https://registry.hub.docker.com/'
     DOCKER_PROD_REGISTRY_URL = 'https://index.docker.io/v1/'
     DOCKER_REGISTRY_CREDENTIALS = 'global-docker-registry-credentials'
     DOCKER_IMAGE_NAME = "msiddiq123/country-curd-rest-service"
     //DOCKER_IMAGE_TAG = "img-${env.BUILD_ID}"
     DOCKER_IMAGE_TAG = "${env.BRANCH_NAME}_${PROJECT_VERSION}"
     DOCKER_REGISTRY_IMAGE = "${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"           
   }
   
   options {     
     timestamps()
     timeout(time: 15, unit: 'MINUTES')   
     buildDiscarder(logRotator(numToKeepStr: '15'))
     //retry(2)     
   }
     
   stages {   
     stage('Prepare Job') {
	steps { 
	  //sh 'echo "################################## Executing stage - Prepare Build Job ##################################"'
          sh 'cat Dockerfile' 
	 //-----For Linux----
	 //https://www.youtube.com/watch?v=bGqS0f4Utn4
	 //https://www.jenkins.io/doc/pipeline/tour/running-multiple-steps/
	 //https://stackoverflow.com/questions/35043665/change-windows-shell-in-jenkins-from-cygwin-to-git-bash-msys#:~:text=Go%20to%20Manage%20Jenkins%20%3E%20Configure,the%20Execute%20shell%20build%20step.&text=Note%3A%20This%20won't%20work,agents%20(JENKINS%2D38211).
	 //sh 'echo "Hello World"'
        
	//sh '''
               //echo "Multiline shell steps works too"
               //ls -lah
          //'''	    
        }//steps
     }//stage
    }//stages
    
      
}//pipeline