This module provides backend services for an online bookstore application. It utilizes JDK 1.8 and MongoDB as the database server. Additionally, Swagger is integrated to facilitate API documentation.

Installation
Prerequisites
JDK 1.8 or higher installed and configured.
MongoDB server installed and running.
Steps
1-Clone this repository to your local machine.
2-Ensure MongoDB is running.
3-Navigate to the root directory of the project.

Build the project using Maven:
- mvn clean install

Run the application:
- java -jar target/online-bookstore-services.jar

API Documentation
Swagger is integrated into this module for easy API documentation. You can access the Swagger UI by navigating to the following URL in your browser:
-http://localhost:7080/swagger-ui.html#

Usage :
This module provides various endpoints to manage books, users, borrowers, etc. Refer to the API documentation for details on available endpoints and their usage.

Configuration :
MongoDB configuration:
Host: localhost
Port: 27017
Database: bookStore


License
------
