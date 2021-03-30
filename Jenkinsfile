//Assumptions:-
//a)Encoded with - 'Encode in ANSI' in Notepad++
//b)Here we are assuming that the maven build and docker build are happening in the same box where Jenkins server is installed
//c)We are creating a container with replica=1
//d)For 'country-curd-rest-service-job' mutibranch pipeline job, it creates a folder with this name in jenkins dashboard and creates child jobs inside this folder for each of the branches...so you can only configure the parent job and not the child jobs
//Reference:-
//a)https://www.jenkins.io/doc/book/pipeline/
//b)https://www.youtube.com/watch?v=B_2FXWI6CWg

def gvy

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
     stage('Prepare Build Job') {
	steps { 
	   script {
             gv = load "build_scripts/groovy_script.groovy" 
           }
	  echo '################################## Executing stage - Prepare Build Job ##################################'	  
          echo "M2_HOME ====> ${M2_HOME}"
	  echo "PATH ====> ${PATH}"
	  echo 'Checking maven version...'
          bat 'mvn -version' 
	  echo 'Checking docker version...'
	  bat 'docker -v'
	  
	 //-----For Linux----
	 //https://www.jenkins.io/doc/pipeline/tour/running-multiple-steps/
	 //https://stackoverflow.com/questions/35043665/change-windows-shell-in-jenkins-from-cygwin-to-git-bash-msys#:~:text=Go%20to%20Manage%20Jenkins%20%3E%20Configure,the%20Execute%20shell%20build%20step.&text=Note%3A%20This%20won't%20work,agents%20(JENKINS%2D38211).
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
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*' 
	   }
        }
	steps {
	  script {
            gv.buildProj()
          }
	  echo '################################## Executing stage - Build Project ##################################' 
          echo "Building for ====> ${PROJECT_GROUP_ID}:${PROJECT_ARTIFACT_ID}:${PROJECT_VERSION}:${PROJECT_PACKAGING}" 	  
	  bat 'mvn clean install -Dmaven.test.skip=true'
          bat 'dir /p' 	
        }//steps
     }//stage
     
     stage('Build Image On Docker-NonProd') { 
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
	  script {
            gv.buildImage()
          }
	  echo '################################## Executing stage - Build Docker Image ##################################'
	  echo "Building docker image on Docker NonProd Server ===================> ${DOCKER_NON_PROD_SERVER}"
          echo 'Reading Dockerfile...'
	  bat 'type Dockerfile'	  
	  script{
	     //https://www.jenkins.io/doc/book/pipeline/docker/
	     docker.withRegistry(DOCKER_NON_PROD_REGISTRY_URL, DOCKER_REGISTRY_CREDENTIALS) {
                def customImage = docker.build(DOCKER_REGISTRY_IMAGE)               
                customImage.push()
             }	     
	  }//script	 
 	  
	  bat "docker image ls ${DOCKER_REGISTRY_IMAGE}"
	  bat "docker rmi ${DOCKER_REGISTRY_IMAGE}" 
        }//steps
     }//stage 
     
     stage('Build Image On Docker-Prod') { 
        when { 
           allOf{
		expression { 		
		  env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*' 
		}
		expression {
                  params.BuildEnvironment == 'prod'
                }
           } 	              
        }     
	steps {
	  script {
            gv.buildImage()
          }
	  echo '################################## Executing stage - Build Docker Image ##################################'
	  echo "Building docker image on Docker Prod Server ===================> ${DOCKER_PROD_SERVER}"
	  echo 'Reading Dockerfile...'
	  bat 'type Dockerfile'
	  script{
	     docker.withRegistry(DOCKER_PROD_REGISTRY_URL, DOCKER_REGISTRY_CREDENTIALS) {
                def customImage = docker.build(DOCKER_REGISTRY_IMAGE)               
                customImage.push()
             }	     
	  }//script
	  
	  bat "docker image ls ${DOCKER_REGISTRY_IMAGE}"
	  bat "docker rmi ${DOCKER_REGISTRY_IMAGE}" 
        }//steps
     }//stage 
     
     stage('Deploy Docker Image On Docker-NonProd') { 
        when {
             //anyof
	     allOf{
		expression { 
		  env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*'
	        }
		expression {
                  params.BuildEnvironment == 'default' || params.BuildEnvironment == 'dev' || params.BuildEnvironment == 'sit' || params.BuildEnvironment == 'uat' || params.BuildEnvironment == 'pt'                       		       
                }
	        expression {
                  params.CheckDeploy
                }
             }           
        }     
	steps {
	  script {
            gv.deployImage()
          }		  
	  echo '################################## Executing stage - Deploy Docker Image ##################################'
          echo "Deploying docker image on ===================> ${DOCKER_NON_PROD_SERVER}"
	  
	  bat "docker pull ${DOCKER_REGISTRY_IMAGE}"
	  bat "docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 ${DOCKER_REGISTRY_IMAGE}"	  
	  bat "docker ps -aqf ancestor=${DOCKER_REGISTRY_IMAGE}"	  
        }//steps
     }//stage   

     stage('Deploy Docker Image On Docker-Prod') { 
        when {
             //anyof
	     allOf{
		expression { 
		  env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'develop' || env.BRANCH_NAME == 'feature-*' || env.BRANCH_NAME == 'release-*'
	        }
		expression {
                  params.BuildEnvironment == 'prod'
                }
	        expression {
                  params.CheckDeploy
                }
             }           
        }     
	steps {
	  script {
            gv.deployImage()
          }		  
	  echo '################################## Executing stage - Deploy Docker Image ##################################'
          echo "Deploying docker image on ===================> ${DOCKER_PROD_SERVER}"
	  
	  bat "docker pull ${DOCKER_REGISTRY_IMAGE}"
	  bat "docker run -d -it -v /mnt/d/Shared_Project_Home/:/opt/logs/ -p 8081:8081 ${DOCKER_REGISTRY_IMAGE}"	  
	  bat "docker ps -aqf ancestor=${DOCKER_REGISTRY_IMAGE}"	  
        }//steps
     }//stage     
   }//stages
   
   post {
     always {
	echo '################################## Executing post [always] handler ##################################'       
        emailext attachLog: true,
	compressLog: true,
	to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
	mimeType: 'text/plain',
        body: "Hi Team, \n\n Please find the build and console log details below:- \n\n Job Name :: ${env.JOB_NAME} \n GIT Branch :: ${env.BRANCH_NAME} \n GIT Commit ID :: ${env.GIT_COMMIT} \n Project GroupId :: ${PROJECT_GROUP_ID} \n Project ArtifactID :: ${PROJECT_ARTIFACT_ID} \n Project Version :: ${PROJECT_VERSION} \n Project Packaging :: ${PROJECT_PACKAGING} \n Build No. :: ${env.BUILD_NUMBER} \n Build Environment :: ${BUILD_ENV} \n Build Status :: ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"     	
     }
     
     success {
        echo '################################## Executing post [success] handler ##################################'        
	echo 'Job execution succeded...'
	//build job: 'maven-freestyle-test-job', parameters: [[$class: 'BooleanParameterValue', name: 'CodeScan', value: true], [$class: 'BooleanParameterValue', name: 'UnitTest', value: true]]	
     }
     
     failure {
       echo '################################## Executing post [failure] handler ##################################'
       echo 'Job execution failed...'
     }  
   }//post
      
}//pipeline