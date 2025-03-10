pipeline {
    agent any
    tools {
        gradle 'Gradle'
    }
//     environment{
//            DOCKER_HUB_REPO = 'zheki4/webforum'
//     }
    stages {
        stage('Clean and Build ') {
            steps {
                sh 'chmod +x gradlew'

                sh './gradlew bootJar'
            }
        }
        stage('Build docker image') {
            steps {

                 sh 'docker build -t zheki4/webforum:latest .'
                 sh 'docker push  zheki4/webforum:latest'

            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}