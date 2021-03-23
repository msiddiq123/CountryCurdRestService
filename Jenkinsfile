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
    
   stages {        
     stage('Build & Create Artifact') { 
        when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'dev' 
	   }
        }  
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	  
	  echo "Building version ${CODE_VERSION} and will be deploying with ${JENKINS_CREDENTIALS}"
	  echo "PATH ====> ${PATH}"
          echo "M2_HOME ====> ${M2_HOME}"
	  bat 'mvn -version'
        }
     }
           
   }//stages
   
   
}//pipeline