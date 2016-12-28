# Manage Plugins

- To manage plugins go to the Jenkins Home
- Click on Manage Jenkins
- Click on Manage Plugins on the right hand side. This is the place from where you can install, uninstall, enable or disable any plugin.
- Search for a plugin in the search bar, check the plugin and select "Download and install after restart"
- Now check the check box "Restart jenkins when installation is complete and no jobs are running"
- Once jenkins has been restarted then login with the user
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
