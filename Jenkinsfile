//Encoded with - 'Encode in ANSI' in Notepad++
pipeline {

   agent any
   
   tools {
     maven 'Jenkins-Maven'
   }
  
  //Here we are assuming that the maven build and docker build are happening in the same server where Jenkins is installed
   parameters {
     choice(name: 'buildEnvironment', choices: ['defalut', 'dev', 'sit', 'uat', 'pt', 'prod'], description: 'Choose an environment for build server.')
   }
   
   environment {
     GIT_CREDENTIALS = credentials('global-git-credentials')
     JENKINS_CREDENTIALS = credentials('global-jenkins-credentials')
     BUILD_ENV = 'dev OR sit OR uat OR pre OR prod'     
   }
   
   //retry(2) 
   // https://www.jenkins.io/doc/book/pipeline/syntax/#options
   options {     
     timestamps()
     timeout(time: 15, unit: 'MINUTES')   
     buildDiscarder(logRotator(numToKeepStr: '15'))     
   }
  

   //NB:- In Linux we need to execute sh 'echo Executing stage - Build & Create Artifact...' /  sh 'mvn clean install' and in windows we can use bat like bat 'mvn -version'
   stages {
     stage('Prepare Build Job') {
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Prepare Build Job >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
          echo "Choose environment between ${BUILD_ENV} - ${params.buildEnvironment}"
        }
     }
     
     stage('Build Project') {
	when {               
           expression { 
		env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'dev' 
	   }
        }
	steps {
	  echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing stage - Build Project >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
          //Using Jenkins Environment Variable
	  echo "Building and deploying in Jenkins Server with ${JENKINS_CREDENTIALS}"
	  echo "PATH ====> ${PATH}"
          echo "M2_HOME ====> ${M2_HOME}"
          bat 'mvn -version' 
	  bat 'mvn clean install -Dmaven.test.skip=true'
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
     
     //https://plugins.jenkins.io/email-ext/
     success {
        echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post success handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'        
	emailext attachLog: true,
	compressLog: true,
	to: 'maroof.siddique2013@gmail.com',
        subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
	mimeType: 'text/plain',
        body: "Hi Team, \n\n Please find the build and console log details below:- \n Job Name >> ${env.JOB_NAME} \n Build No. >> ${env.BUILD_NUMBER} \n GIT Branch >> ${env.BRANCH_NAME} \n Build Environment >> ${BUILD_ENV} \n Build Status >> ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"     
     }
     
     //https://plugins.jenkins.io/email-ext/
     failure {
       echo '>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Executing post failure handler >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>'
       //mail to: 'maroof.siddique2013@gmail.com',
       //subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${currentBuild.result} !",
       //body: "Please find the build and console log details at ${env.BUILD_URL}" 
       emailext attachLog: true,
       compressLog: true,
       to: 'maroof.siddique2013@gmail.com',
       subject: "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - ${env.BRANCH_NAME} - ${BUILD_ENV} - ${currentBuild.result} !",
       mimeType: 'text/plain',
       body: "Hi Team, \n\n Please find the build and console log details below:- \n Job Name >> ${env.JOB_NAME} \n Build No. >> ${env.BUILD_NUMBER} \n GIT Branch >> ${env.BRANCH_NAME} \n Build Environment >> ${BUILD_ENV} \n Build Status >> ${currentBuild.result} \n Please find the build and console log details at ${env.BUILD_URL} \n\n Thanks,\n Jenkins Build Team"
     }  
   }//post
      
}//pipeline