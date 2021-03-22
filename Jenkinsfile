pipeline {
 
 agent any
 
 environment{
    CODE_VERSION='1.0.0'
    JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')  
 }
 
   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' 
   stages {
        
     stage('Build & Create Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //Using Jenkins Environment Variable
	  echo "Building version ${NEW_VERSION} and will be deploying with ${JENKINS_CREDENTIALS}"
        }
     }
     
     stage('Deploy Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Deploy Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	  
	  //Using Jenkins Credentials only for a particular stage
	  withCredentials([
	      usernamePassword(credentials: 'global-jenkins-credentials', usernameVariable: USER, passwordVariable: PWD)
	  ]){
		echo "Executing scripts with ${USER} and ${PWD}"
	  }
        }
     }
     
     
   }//stages
   
}//pipeline