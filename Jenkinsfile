pipeline {
    agent {label "MRP_DEV"}
    stages{
        stage("Check old image") {
            steps {
                sh 'docker rm -f mks-mrp-be|| echo "this container does not exist" '
                sh 'docker image rm -f mks-mrp-be || echo "this image dose not exist" '
            }
        }
        stage('Clean') {
            steps {
                sh 'chmod +x mvnw'
                sh "./mvnw -ntp clean -P-webapp"
            }
        }
        stage('Validate') {
            steps {
                sh 'chmod +x mvnw'
                sh "./mvnw -ntp validate"
            }
        }
        stage('Packaging') {
            steps {
                sh "./mvnw -ntp verify -P-webapp -Pdev -Dmaven.test.skip -Dcheckstyle.skip"
            }
        }
        stage('Build and Run') {
            steps {
                sh 'docker compose up -d --build'
            }
        }
    }
}
