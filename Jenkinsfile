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
                container('master') {
                    sh 'mvn clean -o -gs `pwd`/configuration/settings.xml test'
                }
            }
        }
    }
}
