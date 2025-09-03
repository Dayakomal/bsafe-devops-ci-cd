pipeline {
  agent any
  environment {
    IMAGE_NAME = "bsafe-app"
    IMAGE_TAG  = "latest"
  }
  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test (Dockerized Maven)') {
      steps {
        sh '''
          docker run --rm -v "$WORKSPACE":/workspace -w /workspace maven:3.9.9-eclipse-temurin-17 mvn -B -q dependency:go-offline
          docker run --rm -v "$WORKSPACE":/workspace -w /workspace maven:3.9.9-eclipse-temurin-17 mvn -B clean test package
        '''
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
      }
    }

    stage('Run Container (8081)') {
      steps {
        sh '''
          docker rm -f bsafe-container || true
          docker run -d --name bsafe-container -p 8081:8081 $IMAGE_NAME:$IMAGE_TAG
        '''
      }
    }

    stage('Smoke Test') {
      steps {
        sh '''
          sleep 5
          curl -f http://localhost:8081 || (docker logs bsafe-container && false)
        '''
      }
    }
  }
  post {
    always {
      sh 'docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}" || true'
    }
    cleanup {
      sh '''
        docker stop bsafe-container 2>/dev/null || true
        docker rm   bsafe-container 2>/dev/null || true
      '''
    }
  }
}
