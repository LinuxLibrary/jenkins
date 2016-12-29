# Scheduled Builds

> To run scheduled builds you should have "Scheduled Build Plugin" installed

- Create a new build 
- Name it and select Free Style Project
- Add a build step "Execute Shell"
```
	sudo cat /var/log/messages
```
- Save the project

**Now within the project in the left pane you should see the ***Schedule Build*** option
