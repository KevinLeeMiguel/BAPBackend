pipeline {
    agent {
        node {
            label 'master'
        }
    }

    parameters {
        string(name:'FIRST', defaultValue: '', description:'')
    }

    environment {
        GITHUB_CREDENTIAL_ID = 'github-id'
    }

    stages {
        stage ('checkout scm') {
            steps {
                checkout(scm)
            }
        }

        stage ('unit test') {
            steps {
                withMaven(maven: 'maven', mavenSettingsConfig: '21eb1f38-e527-44ae-a005-aeb79993e452') {
                    sh 'mvn test'
                }
            }
        }
    }
}
