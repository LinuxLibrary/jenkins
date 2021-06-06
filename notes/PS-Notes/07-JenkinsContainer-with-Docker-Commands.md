# Running Docker Commands in Jenkins (Docker) Container

- Run the Jenkins docker container
- `docker run --name jenkins-docker --detach --privileged --network jenkins --network-alias docker --env DOCKER_HOST=tcp://192.168.1.10:2376 --env DOCKER_TLS_VERIFY=1 --env DOCKER_CERTS_PATH=/certs/client --volume jenkins-docker-certs:/certs/client:ro --volume jenkins-data:/var/jenkins_home --volume /var/run/docker.sock:/var/run/docker.sock --publish 8080:8080 --publish 50000:50000 jenkinsci/blueocean`

- Securing the API
    - On the Docker daemon’s host machine, we need to generate a CA certificate:

    ```
    openssl genrsa -out ca-key.pem 4096
    Generating RSA private key, 4096 bit long modulus (2 primes)
    .........++++
    ...............................................................................++++
    e is 65537 (0x010001)
    Enter pass phrase for ca-key.pem:
    Verifying - Enter pass phrase for ca-key.pem:
    $ openssl req -new -x509 -days 365 -key ca-key.pem -sha256 \
    -out ca.pem
    Enter pass phrase for ca-key.pem:
    You are about to be asked to enter information that will be incorporated
    into your certificate request.
    What you are about to enter is what is called a Distinguished Name or a DN.
    There are quite a few fields but you can leave some blank
    For some fields there will be a default value,
    If you enter '.', the field will be left blank.
    -----
    Country Name (2 letter code) [AU]:IN
    State or Province Name (full name) [Some-State]:Telangana
    Locality Name (eg, city) []:Hyderabad
    Organization Name (eg, company) [Internet Widgits Pty Ltd]:Linux Library
    Organizational Unit Name (eg, section) []:DevOps
    Common Name (e.g. server FQDN or YOUR name) []:aryabhatta.local.com
    Email Address []:mallikbheesetti@gmail.com
    ```

    - We must fill the Common Name field with the FQDN of the docker host machine.
    - Now that we have a CA, we must create a server key and a certificate signing request (CSR):

    ```
    $ openssl genrsa -out server-key.pem 4096
    Generating RSA private key, 4096 bit long modulus (2 primes)
    .............................................................................................................++++
    ....................++++
    e is 65537 (0x010001)
    $ openssl req -subj "/CN=daryabhatta.local.com" -sha256 -new \
    -key server-key.pem -out server.csr
    ```

    - Next, we’ll sign the server CSR with the CA:

    ```
    $ openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem \
    -CAkey ca-key.pem -CAcreateserial -out server-cert.pem
    Signature ok
    subject=CN = aryabhatta.local.com
    Getting CA Private Key
    Enter pass phrase for ca-key.pem:
    ```

    - If we need to establish the SSL connection through the IP address of the server then we must create a certificate extensions file before generating the server certificate:

    ```
    $ echo subjectAltName = \
    DNS:aryabhatta.local.com,IP:10.0.0.100,IP:127.0.0.1 >> extfile.cnf
    $ echo extendedKeyUsage = serverAuth >> extfile.cnf
    $ openssl x509 -req -days 365 -sha256 -in server.csr -CA ca.pem \
    -CAkey ca-key.pem -CAcreateserial -out server-cert.pem \
    -extfile extfile.cnf
    Signature ok
    subject=CN = aryabhatta.local.com
    Getting CA Private Key
    Enter pass phrase for ca-key.pem:
    ```

    - Now, we must generate a client certificate:

    ```
    $ openssl genrsa -out key.pem 4096
    Generating RSA private key, 4096 bit long modulus (2 primes)
    ............++++
    ...................................................................................................++++
    e is 65537 (0x010001)
    $ openssl req -subj '/CN=client' -new -key key.pem -out client.csr
    $ echo extendedKeyUsage = clientAuth > extfile-client.cnf
    $ openssl x509 -req -days 365 -sha256 -in client.csr -CA ca.pem \
    -CAkey ca-key.pem -CAcreateserial -out cert.pem \
    -extfile extfile-client.cnf
    Signature ok
    subject=CN = client
    Getting CA Private Key
    Enter pass phrase for ca-key.pem:
    ```

    - Now modify the docker systemd script as follows `Script path: /lib/systemd/system/docker.service`
    > Note: Take a backup of the file before modifying

    ```
    [Unit]
    Description=Docker Application Container Engine
    Documentation=https://docs.docker.com
    After=network-online.target firewalld.service containerd.service
    Wants=network-online.target
    Requires=docker.socket
    Wants=containerd.service

    [Service]
    Type=notify
    # the default is not to use systemd for cgroups because the delegate issues still
    # exists and systemd currently does not support the cgroup feature set required
    # for containers run by docker
    #ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
    ExecStart=/usr/bin/dockerd --containerd=/run/containerd/containerd.sock
    ExecReload=/bin/kill -s HUP $MAINPID
    TimeoutSec=0
    RestartSec=2
    Restart=always

    # Note that StartLimit* options were moved from "Service" to "Unit" in systemd 229.
    # Both the old, and new location are accepted by systemd 229 and up, so using the old location
    # to make them work for either version of systemd.
    StartLimitBurst=3

    # Note that StartLimitInterval was renamed to StartLimitIntervalSec in systemd 230.
    # Both the old, and new name are accepted by systemd 230 and up, so using the old name to make
    # this option work for either version of systemd.
    StartLimitInterval=60s

    # Having non-zero Limit*s causes performance problems due to accounting overhead
    # in the kernel. We recommend using cgroups to do container-local accounting.
    LimitNOFILE=infinity
    LimitNPROC=infinity
    LimitCORE=infinity

    # Comment TasksMax if your systemd version does not support it.
    # Only systemd 226 and above support this option.
    TasksMax=infinity

    # set delegate yes so that systemd does not reset the cgroups of docker containers
    Delegate=yes

    # kill only the docker process, not all processes in the cgroup
    KillMode=process
    OOMScoreAdjust=-500

    [Install]
    WantedBy=multi-user.target
    ```

    - Now add additional configs to `/etc/docker/daemon.json`

    ```
    {
        "hosts": ["fd://", "tcp://0.0.0.0:2376", "unix:///var/run/docker.sock"],
        "tlscacert": "/etc/docker/certs/ca.pem",
        "tlscert": "/etc/docker/certs/server-cert.pem",
        "tlskey": "/etc/docker/certs/server-key.pem",
        "tlsverify": true
    }
    ```

    - Reload the systemd daemon

    ```
    $ sudo systemctl daemon-reload
    ```

    - Restart docker service

    ```
    $ sudo systemctl restart docker
    ```

    - Now copy the client certs to the docker client (Either on the same host/docker container)
    - In my case I am using docker from
        - `arjun` user on my localhost
        - `jenkins` user on `jenkins-docker` container
    - Now let us copy the client certs to both of the users

        - `arjun` user on my localhost

        ```
        $ mkdir -p /home/arjun/.dokcer && cp -rv /etc/docker/certs/{ca,cert,key}.pem /home/arjun/.docker/ && sudo chown -R arjun:arjun /home/arjun/.docker
        ```

        - `jenkins` user on `jenkins-docker` container
            - At first we need to backup the existing certs
            - Login to the container and rename the files

            ```
            $ docker exec -it jenkins-docker /bin/bash
            ```

            - Rename the files
            - Come out of the container (Ctrl+D)
            - Copy the client certs (`ca.pem`,`cert.pem`,`key.pem`) from `/etc/docker/certs` to container

            ```
            $ docker cp /etc/docker/certs/ca.pem jenkins-docker:/var/jenkins_home/.docker/
            $ docker cp /etc/docker/certs/cert.pem jenkins-docker:/var/jenkins_home/.docker/
            $ docker cp /etc/docker/certs/key.pem jenkins-docker:/var/jenkins_home/.docker/
            ```
    
    - Verify the docker daemon with the following command and ensure there are no SSL/TLS/API related warnings/errors

    ```
    $ docker -H tcp://0.0.0.0:2376 --tls info
    ```

    - Now let us try to run some docker commands from the jenkins docker container
    > Note: It should show both Server and Client versions. 
    > FYI: We haven't installed docker server in the container but we are accessing the docker daemon from the docker host

    ```
    $ docker exec jenkins-docker docker version
    ```

    ```
    $ docker exec jenkins-docker docker image ls
    $ docker exec jenkins-docker docker ps -a
    ```