pipeline {
	agent any 

	node {
		try {
			stage('echo') {
				sh 'echo "Hello World!'
				def obj = null
				sh "${obj.class}"
			}
		} catch (NullPointerException e) {
			error 'broken pipeline - null pointer exception'
			currentBuild.result = 'FAILURE'
			// currentBuild.result = 'UNSTABLE'
		} finally {
			stage('Send Notification') {
				mail to: 'vmsnivas@gmail.com',
					body: "Something is wrong with ${env.BUILD_URL}",
					subject: "Failed pipeline: ${currentBuild.fullDisplayName}"
			}
		}
	}
}