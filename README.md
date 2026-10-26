# Employee Service

A REST API for managing employee records, built with Java and Spring Boot.

## Local running

### Requirements
 - Docker Desktop
 - Java JDK

### Build
Build the projects by running the below command from the project root:
```bash
./gradlew build
```
### MongoDB Instructions
Repository includes a docker compose file to create the mondo DB required for the
application. Run the below command from the terminal to start the database:
```bash
docker compose up -d
```
If you wish to stop the database, run the below command, although be aware any
data persisted will be lost
```bash
docker compose down
```
### Application start
Use your IDE to run the `main()` method in `EmployeeServiceApplication` or 
alternatively run the below command from the project root:
```bash
./gradlew bootRun
```
Once the application is running, the api will be available at http://localhost:8080, you can test this by hitting
`GET /healthcheck` endpoint at: http://localhost:8080/healtcheck

## Testing
Unit tests can be ran either via your IDE directly in `EmployeeServiceTests`
or by running the below command from the project root:
```bash
./gradlew test
```
Component integration tests are a plan for this repository however the time box
has passed to implement them. These can be addressed at the subsequent technical
interview.
