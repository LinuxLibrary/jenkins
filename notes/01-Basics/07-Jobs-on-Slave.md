# Running jobs on slave

Let us create a build first.

- Create New Item
- Give the name as SlaveTest1
- Select ***Free Style Project***
- Click OK
- Now in the ***General*** tab check on ***Restrict where the project can be run***
- Give the lable of the slave ***MySlave-1***
- In the ***Build*** section select ***Add Build Step*** and select ***Execute Shell***
- In the command section let us try to get some logs
```
	sudo cat /var/log/messages
```
- Save the project and run the build


- Now we want to configure a build on a slave node which connect to some other node and run jobs remotely

> For doing that we need to ensure that we have jenkins user on that host and have our ssh keys copied from our slave node.

- Now we need to add that host details on our Jenkins Server from the UI
- Go to ***Manage Jenkins***
- Configure System
- Under the ***SSH Remote Hosts*** click on ***Add*** to add another remote host
- Give the hostname, port, username, keyfile path

- Now let us create a new build which we want to run through the Slave on another remote host
- Create a build
- Name it and select it as a free style project
- Restrict the build to run on slave
- In the ***Build*** section add and select ***Execute shell script on remote host using ssh***
- In the ***SSH Site*** select your user@remote_host
- Let us try to get the details of w command this time
```
	w
```
- Save and run the build
