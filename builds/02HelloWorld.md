pipeline {
	agent any

	node {
		stage('Stage 1') {
			echo 'Hello World'
		}
		stage('Stage 2') {
			echo 'We are in stage 2'
		}
	}
}