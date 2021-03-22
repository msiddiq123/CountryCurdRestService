pipeline {
 
 agent any
 
 tools{
   maven 'Jenkins-Maven'
 }
 
 environment{
    CODE_VERSION='1.0.0'
    GIT_CREDENTIALS = credentials('global-git-credentials')
    JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')    
 }
 
   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' 
   stages {
        
     stage('Build & Create Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //Using Jenkins Environment Variable
	  echo "Building version ${CODE_VERSION} and will be deploying with ${JENKINS_CREDENTIALS}"
	  echo "PATH ====> ${PATH}"
          echo "M2_HOME ====> ${M2_HOME}"
	  
        }
     }
     
     stage('Deploy Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Deploy Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	  
	  //Using Jenkins Credentials only for a particular stage
	  withCredentials([
	      usernamePassword(credentialsId: 'global-jenkins-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')
	  ]){
		//https://wiki.jenkins.io/display/JENKINS/Credentials%20Binding%20Plugin
		echo "Executing scripts with ${USERNAME} and ${PASSWORD}"
	  }
        }
     }
     
     
   }//stages
   
}//pipeline