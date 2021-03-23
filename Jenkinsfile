//Encoded with - 'Encode in ANSI' in Notepad++
pipeline {

   agent any
   
   tools{
     maven 'Jenkins-Maven'
   }
  
   environment{
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')    
   }

   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' and in windows we can use bat like bat 'mvn -version'
   stages {
     stage('Build & Create Artifact') {
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build & Create Artifact >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
          //Using Jenkins Environment Variable
	  echo "Building and deploying in Jenkins Server with ${JENKINS_CREDENTIALS}"
	  echo "PATH ====> ${PATH}"
          echo "M2_HOME ====> ${M2_HOME}"
          bat 'mvn -version' 	  
        }
     }
   }//stages
   
   post{
     always{
	echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post handler - always >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	
        mail to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${currentBuild.result} !",
        body: "Please find the build and console log details at ${env.BUILD_URL}" 	
     }
     
     success{
        echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post handler - success >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
        echo "${env.JOB_NAME} executed successfuly..."	
     }
     
     failure{
       echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post handler - failure >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
       echo "${env.JOB_NAME} failed to execute successfuly..."	       
     }   
   }//post
      
}//pipeline