pipeline{
    agent any
    environment {
         wars = findFiles(glob: '**/target/*.war')
         jars = findFiles(glob: '**/target/*.jar')
       }
        parameters {
            string(name: 'staging_server', defaultValue: 'staging-server', description: 'Staging Server')
            string(name: 'war_name', defaultValue: 'booksmanagement.war' , description: 'war name')
            string(name: 'jar_name', defaultValue: 'booksmanagement.jar' , description: 'jar name')
            string(name: 'maven_profiles', defaultValue: '', description: 'Maven build profile')
            string(name: 'maven_params', defaultValue: '', description: 'Maven build paramenters')
            string(name: 'path_of_jar', defaultValue: '', description: 'path of jar')
            string(name: 'path_of_war', defaultValue: '', description: 'path of war')
            string(name: 'user', defaultValue: 'user', description: 'user on staging server')
            booleanParam(name: 'no_build', defaultValue: false, description: 'Skip mvn build')
            booleanParam(name: 'no_test',  defaultValue: true, description: 'Skip mvn test')
            string(name: 'docker_services', defaultValue: '', description: 'Docker compose service to restart')
        }
        triggers {
            pollSCM('* * * * *')
        }

    stages{
        stage('packaging'){
            steps {
                script {
                    if (params.no_build) {
                        echo 'Maven build ignored'
                    } else {
                        sh 'mvn clean package -P war ${maven_params}'
                        sh 'mvn package ${maven_params}'
                    }
                    if (params.build_docker) {
                        echo 'Build Docker images'
                        sh 'docker build ./postgres'
                        sh 'docker build ./bm_boot'
                        sh 'docker build ./bm_tomcat'
                    }
                }
            }
            post {
                success {
                    echo 'Now Testing...'
                }
            }
        }
        stage('testing'){
            steps {
                script {
                    if (params.no_test) {
                        echo 'Maven test ignored'
                    } else {
                        sh 'mvn verify ${maven_params}'
                    }
                }
            }
            post {
                success {
                    echo 'Now Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                    archiveArtifacts artifacts: '**/target/*.jar'
                }
            }
        }
        stage ('Deploy to Staging'){
            steps {
                timeout(time: 3, unit: 'DAYS') {
                    input message: 'Deploy this build to Staging server?' submitter: ${user}
                }
                echo "Copy File to deploy war:${wars} on server:${staging_server} in PATH:${path_of_war}"
                sh "scp target/*.war ${user}@${staging_server}:${path_of_war}"

                echo "Copy File to deploy jar:${jars} on server:${staging_server} in PATH:${path_of_jar}"
                sh "scp target/*.jar ${user}@${staging_server}:${path_of_jar}"
            }
            post {
                success {
                    echo "Rebuild/Restart container:${docker_services} on ${staging_server} in "
                    sh "ssh -tt jenkins@${staging_server} \"sudo docker-compose -f ${path_of_jar}docker-compose.yml up -d --no-deps --force-recreate --build ${docker_services}\" "
                }
            }
        }
    }
}