pipeline {
    agent any

    stages {

        stage('Compile') {
            steps {
                bat 'javac LoanProcessingSystem.java LoanProcessingQA.java'
            }
        }

        stage('Run QA Tests') {
            steps {
                bat 'java LoanProcessingQA'
            }
        }
    }

    post {
        success {
            echo 'Banking Loan Approval Pipeline completed successfully.'
        }

        failure {
            echo 'Banking Loan Approval Pipeline failed.'
        }
    }
}
