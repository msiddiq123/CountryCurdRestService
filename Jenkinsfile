pipeline {
 
 agent any
 
   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' 
   stages {
        
     stage('Build & Create Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  mvn -version
        }
     }
     
     stage('Deploy Artifact') { 
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Deploy Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	  
	  sh 'mvn -version'
        }
     }
     
     
   }//stages
   
}//pipeline