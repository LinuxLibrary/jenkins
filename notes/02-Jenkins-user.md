# Preparing ENV Build Accounts

- Mostly we can use the default jenkins user "jenkins" for builds. But we will not be able to login to that user as the login shell will be /bin/false for jenkins. So for that we need to change the default login shell for "jenkins" user.

- Set a password for "jenkins" user

- We can use this user to run all our builds. By default this is a non-privllaged user.

- Now create ssh keys and copy to jenkins user
```
# ssh-keygen (Leave all defaults as is)
# ssh-copy-id jenkins@localhost
```

- Now add sudo privillages to "jenkins" user without asking any password
```
# visudo

jenkins	ALL=(ALL)	NOPASSWD:ALL
```

- Add jenkins user on the slave node as well along with sudo privillages as above

- Now from the master server copy the ssh key to the slave server
```
# su - jenkins
$ ssh-copy-id jenkins@slave
```
