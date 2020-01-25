pipeline {
	agent any

	node {
		stage('CheckOut') {
			git 'https://github.com/LinuxLibrary/jenkins.git'
		}
	}
}