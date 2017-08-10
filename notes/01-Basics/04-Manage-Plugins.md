# Manage Plugins

- To manage plugins go to the Jenkins Home
- Click on Manage Jenkins
- Click on Manage Plugins on the right hand side. This is the place from where you can install, uninstall, enable or disable any plugin.
- Search for a plugin in the search bar, check the plugin and select "Download and install after restart"
- Now check the check box "Restart jenkins when installation is complete and no jobs are running"
- Once jenkins has been restarted then login with the user

# Let us try to configure builds on remote hosts

> Before doing this install SSH plugin for GIT

- Now to configure the hosts or sites which we want to ssh to then go to Manage Jenkins
- Click on "Configure System"
- Under the SSH Remote Connections add the SSH sites/hosts
	- Give the hostname
	- Port Number
	- Username
	- Password

	> If you don't want to go with the password then leave it blank and provide the key file location

	- Key File location
- Save the details
- Previously we are executing the builds on the local jenkins server, but we now can execute the builds on remote hosts through SSH.
- Let us configure a build for that
	- Create a new build
	- Select FreeStyle Project
	- Name the project
	- Give a description
	- Now under the ***Build*** section select ***Execute shell script on remote host using ssh***
	- Select the host/site
	- Save and execute the build

	> If you get an error for tty then disable requirettyin /etc/sudoers

# Let us now configure a GitHub build

- Create a project
- Select FreeStyle Project
- Name the Project
- Describe it
- Check the ***GitHub Project*** and provide the GIT url
- Under ***Source Code Management*** select Git and give the Repository url same as the Project URL

> In order to run this project successfully we need to have GIT installed on both master and slave/remote server
