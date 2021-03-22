pipeline {
 
 agent any
 
   stages {
   
     //NB:- sh works for Linux and bat works for windows
     stage('Build & Create Artifact') { 
	steps {
	   bat 'echo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //sh 'echo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //sh 'mvn -version'
        }
     }
     
     stage('Deploy Artifact') { 
	steps {
	  bat 'echo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //sh 'echo >>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Deploy Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
	  //sh 'mvn -version'
        }
     }
     
     
   }//stages
   
}//pipeline