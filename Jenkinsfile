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
                sh 'mvn test'
            }
        }
    }
}
