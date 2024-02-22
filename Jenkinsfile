pipeline {
    agent {label "mrp-test-gd2"}
    stages{
        stage("Check old image") {
            steps {
                bat 'docker rm -f mrp-be|| echo "this container does not exist" '
                bat 'docker image rm -f mrp-be || echo "this image dose not exist" '
            }
        }
        stage('Clean') {
            steps {
                bat "./mvnw -ntp clean -P-webapp"
            }
        }
        stage('Validate') {
            steps {
                bat "./mvnw -ntp validate"
            }
        }
        stage('Packaging') {
            steps {
                bat "./mvnw -ntp verify -P-webapp -Pdev -Dmaven.test.skip -Dcheckstyle.skip"
            }
        }
        stage('Build and Run') {
            steps {
                bat 'docker compose up -d --build'
            }
        }
    }
}
