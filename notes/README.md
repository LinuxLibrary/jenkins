# Continous Integration with Jenkins
# Installation process of Jenkins

- Download and install latest version of JDK (Java 7 or newer version is recomended)

- Add the Jenkins repository to the yum repos, and install Jenkins from here.
	* sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
	* sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
	* sudo yum install jenkins

- Turn jenkins service at startup and restart the service
	* chkconfig jenkins on
	* service jenkins start

- Open your browser and hit the below URL to open Jenkins UI
	http://localhost:8080/

- It will now prompt for the Initial Admin password which is available in the below file
	/var/lib/jenkins/secrets/initialAdminPassword
  Copy that password and paste in the field in the Jenkins web UI

- Now it will prompt for the installation of Jenkins Plugins 
	Go for the default.

- You are all set and the final step is that you need to create a Jenkins user.
