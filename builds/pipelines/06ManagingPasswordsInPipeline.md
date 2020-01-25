pipeline {
	agent any

	node {
		stage('UsePasswords') {
			withCredentiials([
				usernameColonPassword(credentialsId:'my_id_1',
				variable:'MY_CREDENTIALS_1')]) {
					echo "My password is ${MY_CREDENTIALS_1}"
				}
		}
	}
}