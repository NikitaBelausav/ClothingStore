# cs336-buyme

CS336 final project, an online auction site.

## Requirements

- **JDK:** Version 11 or higher
- **MySQL**: Version 8.0 or higher

## Gradle

The project is backed by the Gradle build tool. 
In order to run any of the tasks below, you can either interface with it via your IDE,
or by using the `gradlew` script included in the project:

- Windows: `gradlew.bat <task>`
- *NIX (macOS, Linux, BSD, etc.): `./gradlew <task>`

## Building

You can simply use the `war` task to compile to a WAR for deployment with Tomcat Apache or any other Java EE Webserver. 
Compiled WAR files are located in `build/libs`.

## Testing

For a standardized build of MySQL and the starting database to use for the application, you can use `docker-compose` to spin up a MySQL server and PHPMyAdmin interface. SQL files located in `src/main/sql` will be used to initialize the database. The servers can be accessed as follows:

- MySQL: `localhost:3306`
- PHPMyAdmin: `localhost:8088`

For running a standardized Tomcat instance, the `tomcat` gradle plugin comes with some tasks to allow you to run a Tomcat container through Gradle.
Due to limitations with the `tomcat` plugin, hotswapping Java classes is not possible, however JSP hotswapping is still available.

- `tomcatRun`: Run Tomcat with files in-place.
- `tomcatRunWar`: Compiles the code base into a WAR, which is then run in a Tomcat container.
- `tomcatStop`: Required to run after stopping any of the `tomcatRun` tasks to stop the Tomcat container.

The container can be accessed at `http://localhost:8090/cs336-buyme`.

## Credentials
Listed below are the test credentials currently registered with the webapp:
| Login   | Password    | Type |
|--------------- | --------------- | --------------- |
| admin   | admin   | `ADMIN`
| rep   | rep  | `CUSTOMER_REP`
| dorianht   | password1   | `END_USER`
| endoman123   | password2   | `END_USER`
| muskanb12   | password3   | `END_USER`
| windhollow   | password4   | `END_USER`
