# Working with a Jenkins Build Slave Node

> Ensure that you have a slave node with ssh keys copied from master and also the credentials of the jenkins user of the slave node have been configured on the master. Also install GIT, java on the slave node. Java will also be configured by the master node as well.

- Login to the slave node as jenkins
- Go to the jenkins home directory
- Create a directory to run and store the builds
```
	mkdir jenkins_slave
```
- Now go to the Jenkins UI
- Go to ***Manage Jenkins*** and then to ***Manage Nodes***
- Now click on ***New Node*** to create a new node
- Name the Node as ***MySlave-1***
- Select ***Permanent Agent***
- In the next screen describe the host
- Give the number of build executors (Select double than that of the master) or as per your choice
- Give the remote root directory
```
	/home/jenkins/jenkins_slave
```
- Give the lable same as the name of the node
- Select the Launch Method as *** Launch slave agents on Unix Machine via SSH***
- Provide the slave host name
- Add credentials for jenkins user

> Be sure to select the Kind as ***SSH Username with Private Key***

```
	Username : jenkins
	Private Key : From the jenkins master ~/.ssh
	ID : JenkinsUserSSHKey (Any unique name as per our preference)
	Description : As needed
```
- Click Add and then click Save
- If the node don't have java installed then the master will do the necessary installations and configs
