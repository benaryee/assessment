# Application README

## Running the Application Without Docker

Before running the applications, ensure that you have the following software installed on your system:

- Java 17 or higher
- Maven 3.3.9 or higher
- PostgreSQL 15 or higher

### Steps to Run the Applications

1. **Build the Applications:**

   To build the applications, navigate to the root folder of each application and execute the following command:

   ```bash
   mvn clean package -P[environment]
   ```
    
    where `[environment]` is the environment you want to build the application for. The available environments are: `dev`, `example`.
   A sample profile is provided in the `application-example.properties` file for each environment. You can modify the profiles as per your requirements.


2. **Run the Applications:**
   ```bash
   mvn spring-boot:run
    ```


A postman documentation has been published [here](https://documenter.getpostman.com/view/6029718/2s9YC4UCWJ)


[//]: # (TODO: Add instructions for running the application using Docker and Docker Compose)