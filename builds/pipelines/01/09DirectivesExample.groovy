pipeline {
	agent any

	tools {
		maven 'M3'
	}

	parameters {
		string(name: 'VERSION',
			defaultValue: '1.0.0',
			description: 'What is the version to build?')
	}

	stages {
		stage('Build') {
			steps {
				sh "./build.sh ${params.VERSION}"
				script {
					for (int i = 0; i < 5; ++i) {
						echo "Printing number ${i}"
					}
				}
			}
		}
	}
}