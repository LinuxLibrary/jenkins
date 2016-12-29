# Scheduled Builds

> To run scheduled builds you should have "Scheduled Build Plugin" installed

- Create a new build 
- Name it and select Free Style Project
- Add a build step "Execute Shell"
```
	sudo cat /var/log/messages
```
- Save the project
- Now within the project in the left pane you should see the ***Schedule Build*** option
- Through this option we can schedule the build to run a a particular time.
- The another way to run scheduled builds is we need to configure the project
- Go to the ***Build Triggers*** section
- Select ***Build Periodically***
- Here the format used to schedule the builds is **Cron Job Format***
```
Minutes		Hours	DayOfMonth(Date)	Month	DayOfWeek(DOW)
```
