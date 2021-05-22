# Declarative Pipeline

- Let us configure a Pipeline job now.
  - We have 2 different pipeline jobs
    - **Scripted**: Starts with a `node` block
    - **Declarative**: Starts with a `pipeline` block
- Let us use the Declarative pipeline for our case now and will create a pipeline job which would do the same as [03-FreeStyle-Project-with-Post-Actions.md](03-FreeStyle-Project-with-Post-Actions.md)
- Go to the Dashboard and Click on `New Item`
- Input a name to your project
- Select `Pipeline` and Click Ok
- In the pipeline block input the following script

```
pipeline {
    agent any

    stages {
        stage ('CheckOut') {
            steps {
                git url: 'https://github.com/LinuxLibrary/jgsu-spring-petclinic', branch: 'main'
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
        }
    }
}
```
