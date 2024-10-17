pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/galtiparmak/REST-GraphQL']])
                sh 'mvn clean install'
            }
        }
        stage('Build Docker Image'){
            steps{
                script{
                    sh 'docker build -t gekoline/graphql .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                        sh 'docker login -u gekoline -p ${dockerhubpwd}'
                    }
                   sh 'docker push gekoline/graphql'
                }
            }
        }
    }
}