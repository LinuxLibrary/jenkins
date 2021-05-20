# Install Jenkins Through Docker

> Please be sure to install Docker

- Once you have docker running on your machine, pull the `jenkins` image from the `jenkins` repository.

```
$ docker pull jenkins/jenkins
```

- Run the docker container using the below options as of your choice
  - `--name`: Name of the container
  - `--rm`: Remove the container if it is stopped
  - `--detach|-d`: Run in detached mode
  - `--network`: Connect a container to a network
  - `--network-alias`: Add network-scoped alias for the container
  - `--env`: Set environment variables
  - `--volume|v`: Bind mount a volume
  - `--publish|-p`: Publish a container's port(s) to the host
  - `--privileged`: Give extended privileges to this container

```
$ docker run --name myjenkins --detach --privileged -p 80:8080 -p 50001:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:latest
```

- Once the container is up then get the Initial Jenkins Administrator password

```
docker exec jenkins-docker cat /var/jenkins_home/secrets/initialAdminPassword
```

- Copy the password, Open jenkins url `http://localhost:8080` and paste it to run Jenkins
- Install the recommended plugins
- Create admin user
