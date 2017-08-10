# Jenkins Build Intro

- Create a new build
- Select item name
- Let us create a folder to store our builds
	- Select Folder and name the project as MyBuilds
	- Provide some Display Name and Description for this project
	- Save it
- Now go back to the dashboard and click on the folder
- Now create a new project
	- Select item type as Free Style Project and name the project
	- Give the Description of the Project
	- Now let us try to get the logs 
	- Go to the Build section, Add Step and select Execute Shell
	- In the command section provide your commands
	```
	sudo cat /var/log/messages
	```

	> We need to use sudo as builds will be ran with the "jenkins" user by default
	
	- Save the project

> While we try to run the build it will show error due to the sudo settings for default tty. We need to disable that.


- As root edit the /etc/sudoers file and search for "requiretty" and comment it. That line should look like below
```
Defaults	!requiretty
```

- Now try to run this build and you should get this succeeded.
