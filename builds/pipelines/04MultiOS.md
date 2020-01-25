pipeline {
	agent any

	node {
		stage('List Directory') {
			if (isUnix()) {
				sh 'ls -ltrh'
			} else {
				bat 'dir'
			}
		}
	}
}