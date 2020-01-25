pipeline {
	agent any

	node {
		stage('Multiline') {
			sh 'echo "Hello World"'
			sh '''
				echo "Multiline shell steps works too..."
				ls -ltrh
			'''
		}
	}
}