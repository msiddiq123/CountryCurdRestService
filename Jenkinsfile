pipeline {
 
 agent any
 
   stages {
   
     stage('Build & Create Artifact') { 
	steps {
	  sh echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  sh 'mvn -version'
        }
     }
     
     stage('Unit Test') { 
	steps {
	  sh echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Unit Test >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  sh 'mvn -version'
        }
     }
     
     stage('Static Code Scan') { 
	steps {
	  sh echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Static Code Scan >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  sh 'mvn -version'
        }
     }
     
     stage('Deploy Artifact') { 
	steps {
	  sh echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Deploy Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  sh 'mvn -version'
        }
     }
     
     
   }//stages
   
}//pipeline