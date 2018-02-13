# SonarrCalendarSync
Keeps your [Sonarr](https://github.com/Sonarr/Sonarr) calendar in sync with your Google, Exchange, Office 365, and Apple calendars, by using [Cronofy](https://www.cronofy.com/).

![Example](https://i.imgur.com/9TQLkzi.png)

## Installation instructions
- Download the jar from the [releases](https://github.com/StevenMassaro/SonarrCalendarSync/releases) page
- Create a `prefs.properties` file in the same directory as the jar, and complete it to the specs below
- Open a command window and do `java -jar SonarrCalendarSync.jar`
- Sync should occur automatically. Report any errors/issues here please!

## Prefs.properties specifications
Here is a sample `prefs.properties` file, with explanations as necessary:
```
### APPLICATION SETTINGS ###
# Number of days in the past and future to query local calendar API
DayOffset=49
TimeZone=America/New_York

### LOGGING ###
L_APIEndpoint=<endpoint you have deployed logging API to, or if not using, any url, like google.com>
L_APIKey=<endpoint API key, or any string if you are not using>
# Verbose, or Simple
L_ReportingLevel=Verbose

### LOCAL CALENDAR ###
### (this is the calendar that you wish to pull event data from, Sonarr, in this case)
LC_Name=Sonarr
LC_APIKey=<get API key from Sonarr UI>
LC_Endpoint=http://<server name>:7878/api/

### REMOTE CALENDAR ###
### (this is the Cronofy calendar that you wish to sync your events to)
RC_Name=Cronofy
RC_APIKey=<get API key from Cronofy>
RC_Endpoint=https://api.cronofy.com/v1
RC_CalendarId:<get calendar ID from Cronofy>
```

## Dependencies
All dependencies are wrapped into the single jar. You do not need to acquire these dependencies. They are for reference/development purposes only.

- [https://github.com/StevenMassaro/SonarrConnector](https://github.com/StevenMassaro/SonarrConnector)
- [https://github.com/StevenMassaro/CronofyConnector](https://github.com/StevenMassaro/CronofyConnector)
- [https://github.com/StevenMassaro/Logging](https://github.com/StevenMassaro/Logging)
- [https://github.com/google/gson](https://github.com/google/gson)
