pipeline {
  agent any

  tools {
    jdk 'JDK17'   // same name you set in Tools
    maven 'M3'    // same name you set in Tools
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Check Environment') {
      steps {
        bat 'java -version'
        bat 'mvn -v'
      }
    }

    stage('Build') {
      steps {
        bat 'mvn clean package'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
      }
    }
  }
}
