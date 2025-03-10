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
                sh './gradlew clean'
                sh './gradlew bootJar'
            }
        }
        stage('Build docker image') {
            steps {
                docker.build("${DOCKER_HUB_REPO}:latest")
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}