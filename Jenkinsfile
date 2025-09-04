pipeline {
  agent any

  tools {
    jdk 'JDK17'    // must match the names you set in Global Tool Configuration
    maven 'M3'
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Env Check') {
      steps {
        script {
          echo "Running on isUnix() = ${isUnix()}"
        }
        // print Java & Maven versions using correct shell for the OS
        script {
          if (isUnix()) {
            sh 'java -version || true'
            sh 'mvn -v'
          } else {
            bat 'java -version'
            bat 'mvn -v'
          }
        }
      }
    }

    stage('Build') {
      steps {
        script {
          if (isUnix()) {
            sh 'mvn -B clean package'
          } else {
            bat 'mvn -B clean package'
          }
        }
      }
    }

    stage('Archive Artifact') {
      steps {
        archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, allowEmptyArchive: true
      }
    }
  }

  post {
    always {
      junit 'target/surefire-reports/*.xml'
    }
    success {
      echo 'Build OK ✅'
    }
    failure {
      echo 'Build failed ❌ — check above lines for the FIRST error.'
    }
  }
}
