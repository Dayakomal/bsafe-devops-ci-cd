pipeline {
    agent any

    tools {
        maven 'MAVEN_HOME'
        jdk 'JAVA_HOME'
    }

    environment {
        IMAGE_NAME = "bsafe-app"
        IMAGE_TAG = "latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Dayakomal/bsafe-devops-ci-cd.git'
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn -B clean package'
            }
        }

        stage('Run Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $IMAGE_NAME:$IMAGE_TAG .'
            }
        }

        stage('Run Container') {
            steps {
                sh 'docker run -d --name bsafe-container -p 8081:8081 $IMAGE_NAME:$IMAGE_TAG'
            }
        }

        stage('Cleanup') {
            steps {
                sh 'docker stop bsafe-container || true'
                sh 'docker rm bsafe-container || true'
            }
        }
    }
}
