//Encoded with - 'Encode in ANSI' in Notepad++
pipeline {

   agent any
   
   tools{
     maven 'Jenkins-Maven'
   }
  
   environment{
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     JOB_ENV = 'dev'     
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
   
   post('Post Execution Steps') {
     always{
	echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post handler - always >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
        echo 'Job execution completed...'		
     }
     
     success{
        echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post success handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'        
	mail to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${JOB_ENV} - ${currentBuild.result} !",
        body: "Please find the build and console log details at ${env.BUILD_URL}" 
     }
     
     failure{
       echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post failure handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'      
       mail to: 'maroof.siddique2013@gmail.com',
       subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${JOB_ENV} - ${currentBuild.result} !",
       body: "Please find the build and console log details at ${env.BUILD_URL}" 
     }  
   }//post
      
}//pipeline