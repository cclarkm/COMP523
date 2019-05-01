# UNC Hospital School REST API
This is a Spring Boot REST API for UNC Hospital School teachers. It is used in conjunction with a MySQL database and basic web application to allow teachers to add, access, and modify student information.

# Motivation
The UNC Hospital School needed a way to store and use its data in a long-lasting and secure way. This application ensures security through JWT authentication and uses a database which has more storage than their previous application.

# Code Style
We are using GoogleStyle for our code formatting

# Tech/Framework used
**Built with**

1. Spring Boot Framework (CrudRepository and PagingAndSortingRepository)
2. Auth0 JWT
3. ORM via JPA
4. Cloud MySQL

# Features
1. Controller classes for all database tables
	* All classes have endpoints for Retrieving all instances, creating a new instance, updating an instance, deleting an instance
	* Classes such as StudentController, AdmissionController, and VisitInformationController have other endpoints
	* Information on all endpoints is provided in the PostMan docs
2. Security via JSON Web Tokens

# Installation
For issues encountered during installation, please the FAQ page at http://unchospitalschool.web.unc.edu/faq/
* Pull the repo from github at https://github.com/cclarkm/COMP523
* Install Apache Maven
	- https://maven.apache.org/install.html
	
* Obtain credentials for connecting to the database
	- Credentials can be found in the Sensitive Information Page
	- Follow tutorial: https://cloud.google.com/docs/authentication/production#auth-cloud-explicit-java until "Passing the path to the service account key in code"
	- Whitelist the IP of the network where development will occur in Google App Engine using the same login as above
		-Within the console, using the left navigation menu, click on SQL, click on the db, click on connections, scroll to "Public IP" and add your IP network
	
* Download an IDE, such as eclipse
	- https://www.eclipse.org/downloads/
* Once opened, right click in Package Explorer
	- Import -> Maven -> Existing Maven Projects -> navigate to the repository
	- Right click on project -> Maven -> Update Project	
* Right click on the hospitalschool project
	- New -> Source Folder
		- Name the folder "src/main/resources"
		- Open folder
		- There should be an "application.properties" file
			- Copy and paste the text in the Sensitive Information section under "application.properties"
* Run the project via the command line by executing "mvn spring-boot:run", or "mvn appengine:run" to test for deployment readiness
			
# API Reference
https://documenter.getpostman.com/view/6906426/S17qSpLc

# Tests
* Tests are located at hospitalschool/src/test/java/com/unc/hospitalschool/controller
	- The provided tests only test controller methods
* All tests can be ran via the command line by executing "mvn test"
* All tests within a class can be ran via the command line by executing "mvn test -Dtest=NameOfTestClass"
* A specific test can be ran via the command line by executing "mvn test -Dtest=NameOfTestClass#NameOfTestMethod"

# Credits
1. Gage Matthews for idea, database design, and list of needs
2. Professor David Stotts and Dr. Terrell for guidance throughout the project
3. Bruno Krebs for JWT implementation with Spring Boot via https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
