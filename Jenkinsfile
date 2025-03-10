pipeline {
    agent any
    tools {
        gradle 'Gradle'
    }
    environment{
           DOCKER_HUB_REPO = 'zheki4/webforum'
    }
    stages {
        stage('Clean and Build ') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean'
                sh './gradlew bootJar'
            }
        }
        stage('Build docker image') {
            steps {
                 script {
                   docker.build("${DOCKER_HUB_REPO}:latest")
                 }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}