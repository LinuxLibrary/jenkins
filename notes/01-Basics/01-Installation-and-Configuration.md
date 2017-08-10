# Installation and Configuration

OS Platform: CentOS-7.2

- Get the Jenkins YUM Repository
```
# wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
```

- Import the associated key for that repository
```
# rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
```

- Update your YUM repos
```
# yum update
```

- Install Jenkins
```
# yum install jenkins
```

- Install Java 1.8.0 OpenJDK
```
# yum install java-1.8.0-openjdk
```

- Enable Jenkins on startup and start Jenkins
```
# systemctl enable jenkins
# systemctl start jenkins
```

- Install nginx WebServer
We are going to use nginx like a proxy in this case to have a proxy connection to port 8080
```
# yum install nginx
```

- Now add a proxy pass to port 8080 through the nginx config
```
# cd /etc/nginx
# vi nginx.conf

Within server{} go to location and add the following for proxy pass for port 8080. It should look like below

location / {
	proxy_pass http://127.0.0.1:8080;
}
```

- Now enable nginx service and start it
```
# systemctl enable nginx
# systelctl start nginx
```

- Install text based browser Elinks
```
# yum install elinks
```

- Try to load the localhost url without using the port number
```
http://localhost
```

> If you get an error page then set SELinux to permissive mode and restart both Jenkins and Nginx services

- For configuring the SELinux policies you need to install the ***setroubleshoot-server*** and ***selinux-policy-devel***
```
# yum install setroubleshoot-server selinux-policy-devel
```

- Now get the list of http ports that are allowed by SELinux
```
# sepolicy network -t http_port_t
```

- If you don't see the port 8080 in the list of ports displayed then add the port
```
# semanage port -a -t http_port_t -p tcp 8080
```

> Now you can Enforce the SELinux and restart both Jenkins and Nginx

- Now try to open Jenkins from the browser without using the port with no issue
- Unlock the Jenkins server by giving the SystemAdmin password provided in the following file.Copy it and paste it.
```
/var/lib/jenkins/secrets/initialAdminPassword
```

- Install the suggested plugins which will give you the common plugin which are used on a regular basis
- Create First Admin User
- Now go to "Manage Jenkins". You will be seeing an error on the top which is a proxy error. For this you need to add some proxy configs which are given by Jenkins
- Click on More Info and click on the link "Running Jenkins behind nginx" within the page.
- Go and grab the content after the line starts with "proxy_pass" and ends within the same block
- Paste that into your nginx config after the "proxy_pass" in the location and restart nginx
- Now reload the webpage and go to Manage Jenkins and now you should not see the proxy error
