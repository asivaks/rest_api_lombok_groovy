# rest_api_lombok_groovy

Small Java test project for practicing REST API automation with JUnit 5, Rest Assured, Lombok, Groovy JSON path expressions, and Allure reporting.

The tests use the public ReqRes API as the target service and demonstrate how to:

- define reusable Rest Assured request and response specifications
- deserialize JSON responses into Lombok-backed Java models
- map snake_case JSON fields with Jackson annotations
- assert response status and body content with Hamcrest matchers
- filter JSON arrays with Groovy path expressions in Rest Assured
- produce Allure-compatible test output

## Project structure

```text
src/test/java/
  models/
    User.java
    UserData.java
  specs/
    SingleUserSpecs.java
  tests/
    LombokReqresinTests.java
```

## Main test scenarios

`singleUserLombokSpecTest`

Calls `GET https://reqres.in/api/users/1`, extracts the response into `User` and `UserData` model classes, and checks the user id and email.

`listUsersGroovyTest`

Calls `GET https://reqres.in/api/users?page=1`, filters returned users by email with a Groovy expression, and extracts the first names from the response.

## Tech stack

- Java
- Gradle
- JUnit 5
- Rest Assured
- Lombok
- Jackson annotations
- Hamcrest
- Allure
- SLF4J simple logger

## Running tests

Run the test suite with Gradle:

```sh
gradle test
```

## Notes

This repository does not contain application code. It is a compact learning project focused on REST API test patterns.
