pipeline {
    agent {label "MRP_RD"}
    stages{
        stage("Check old image") {
            steps {
                sh 'docker rm -f mrp-backend || echo "this container does not exist" '
                sh 'docker image rm -f mrp-service-mrp-backend || echo "this image dose not exist" '
            }
        }
        stage('clean') {
            steps {
                sh 'chmod +x mvnw'
                sh "./mvnw -ntp clean -P-webapp"
            }
        }
        stage('packaging') {
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
