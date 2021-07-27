# User Profile API
Basic crud operations on user profile using Spring Boot

# Requirements
- OpenJDK 11
- Gradle

# Prequisite
Please build the project and you should see gradle successfully downloading all the deps. Once build is success we are good to go!!

# Running the application locally
There are several ways to run a Spring Boot application on your local machine. Easiest is to execute the main method in the com.assignment.userprofile.UserprofileApplication class from your IDE.

# Things to note
- The application will run on the port 8881 locally.
- All the available operations are listed in the open-api (swagger doc). Access it using http://localhost:8881/swagger-ui-user-profile.html
- The application uses h2 in memory database to store and retrieve user profiles. Access h2 repository using http://localhost:8881/h2-console
- Application has unit test coverage of 100% on service & controller layer. And >85% coverage overall.
- Postman collection for the same can be found here - https://www.getpostman.com/collections/c897cb817a1b24cae24f
