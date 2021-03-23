//Encoded with - 'Encode in ANSI' in Notepad++
pipeline {

   agent any
   
   tools {
     maven 'Jenkins-Maven'
   }
  
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     JOB_ENV = 'dev'     
   }

   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' and in windows we can use bat like bat 'mvn -version'
   stages {
     stage('Build Project') {
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build Project >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
          //Using Jenkins Environment Variable
	  echo "Building and deploying in Jenkins Server with ${JENKINS_CREDENTIALS}"
	  echo "PATH ====> ${PATH}"
          echo "M2_HOME ====> ${M2_HOME}"
          bat 'mvn -version' 	  
        }
     }
     
     stage('Build Docker Image') { 
        when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'dev' 
	   }
        }     
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build Docker Image >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'	  
	  //Using Jenkins Credentials only for a particular stage
	  withCredentials([
	      usernamePassword(credentialsId: 'global-jenkins-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')
	  ]){
		//https://wiki.jenkins.io/display/JENKINS/Credentials%20Binding%20Plugin
		echo "Connecting to Jenkins Server with ${USERNAME} and ${PASSWORD}"
	  }
        }
     } 
   }//stages
   
   post {
     always {
	echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post handler - always >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
        echo 'Job execution completed...'		
     }
     
     success {
        echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post success handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'        
	emailext attachLog: true,
	mail to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${JOB_ENV} - ${currentBuild.result} !",
        body: "Please find the build and console log details at ${env.BUILD_URL}"        
     }
     
     failure {
       echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post failure handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'      
       emailext attachLog: true,
       mail to: 'maroof.siddique2013@gmail.com',
       subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${JOB_ENV} - ${currentBuild.result} !",
       body: "Please find the build and console log details at ${env.BUILD_URL}"
     }  
   }//post
      
}//pipeline