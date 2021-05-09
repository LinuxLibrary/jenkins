# Run Jenkins on Docker container

- Pull docker container

```
docker image pull jenkinsci/blueocean
```

- Run the container

```
docker run --name jenkins-docker --detach --privileged --network jenkins --network-alias docker --env DOCKER_HOST=tcp://docker:2376 --env DOCKER_TLS_VERIFY=1 --env DOCKER_CERTS_PATH=/certs/client --volume jenkins-docker-certs:/certs/client:ro --volume jenkins-data:/var/jenkins_home --publish 8080:8080 --publish 50000:50000 jenkinsci/blueocean
```

> NOTE: Add --rm option if you want to remove the container if it is stopped

- Get the Initial Jenkins Administrator password

```
docker exec jenkins-docker cat /var/jenkins_home/secrets/initialAdminPassword
```

- Copy the password, Open jenkins url `http://localhost:8080` and paste it to run Jenkins
