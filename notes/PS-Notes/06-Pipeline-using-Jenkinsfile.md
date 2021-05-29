# Pipeline using Jenkinsfile

- At first you need to create a Jenkinsfile with the Pipeline script and keep it besides to your code in your repository.
- I am using the below script file in my case.
- Here I am also using the Poll SCM triggers within the Jenkinsfile itself.
- We can use all the options within the Jenkinsfile which need to configure a job.

> Jenkinsfile

```
#!/usr/bin/env groovy

pipeline {
    agent any
    triggers {
        pollSCM('1 * * * *')
    }
    stages {
        stage ('Checkout') {
            steps {
                git url: 'https://github.com/LinuxLibrary/jgsu-spring-petclinic', branch: 'master'
            }
        }
        stage ('Build') {
            steps {
                sh './mvnw compile'
            }
        }
        stage ('Test') {
            steps {
                sh './mvnw test'
            }
        }
        stage ('Package') {
            steps {
                sh './mvnw package'
            }
        }
    }
    post {
        success {
            junit '**/target/surefire-reports/TEST-*.xml'
            archiveArtifacts 'target/*.jar'
            emailext subject: "Job: \'${JOB_NAME}\' (${BUILD_NUMBER}) ${currentBuild.result}",
                body: "\'${JOB_NAME}\' job has completed with status ${currentBuild.result} \
                \nPlease refer to the build url: ${BUILD_URL}",
                attachLog: true,
                compressLog: true,
                from: 'admin@test.com',
                to: 'arjun@test.com'
        }
    }
}
```

- Go to the Jenkins Dashboard
- Create a Pipeline job
- Click on the `Pipeline` tab
- Click on the dropdown under the `Definition` and select `Pipeline script from SCM`
    - Input the repository URL
    - `https://github.com/LinuxLibrary/jgsu-spring-petclinic`
    - Select your branch if specific (Defaults to `master` branch)
    - In the script path input your Jenkins pipeline script file name if you are using different name (Defaults to `Jenkinsfile`)