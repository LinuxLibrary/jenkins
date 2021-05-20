# Compile and Test Sample Maven project

- Install openjdk if it is not installed yet

```
$ apt install openjdk-15-jdk -y
```

- Configure `PATH` and `JAVA_HOME`
- Add the following lines to `~/.bashrc`

```
export PATH=$PATH:/snap/terraform/current/bin:/usr/lib/jvm/java-15-openjdk-amd64
export JAVA_HOME=/usr/lib/jvm/java-15-openjdk-amd64
```

- Clone the `jgsu-spring-petclinic` repository

```
$ git clone https://github.com/LinuxLibrary/jgsu-spring-petclinic.git
```

- Compile the code

```
$ cd jgsu-spring-petclinic
$ ./mvnw compile
```

- Test the code

```
$ ./mvnw test
```

- You can also use `./mvnw package` option instead of the above, which will perform all the 3 phases compile, test and packaging

```
$ ./mvnw package
```

- Run the application using the package created in the target directory

```
$ java -Dserver.port=8081 -jar target/spring-petclinic-2.3.1.BUILD-SNAPSHOT.jar
```

> Note: -Dserver.port is used to run the application on a specific port. Default port used in the application is 8080
