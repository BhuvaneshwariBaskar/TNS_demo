pipeline {
    agent any
    stages {
        stage("Clean Up"){
            steps {
                deleteDir()
            }
        }
        stage("Clone Repo"){
            steps {
                sh "git clone https://github.com/BhuvaneshwariBaskar/TNS_demo.git"
            }
        }
        stage("Build"){
            steps {
                dir("java-hello-world-with-maven") {
                    sh "mvn test"
                }
            }
        }
        stage("Test"){
            steps {
                dir("java-hello-world-with-maven") {
                    sh "mvn test"
                }
            }
        }  
    }
}
