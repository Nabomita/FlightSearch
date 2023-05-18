
#Flight Search API


	The Flight search api finds flights based on source, destination. Additionally, it sorts the flight details based on duration or price in the ascending/descending order depending on input.


##Project Description


	This project finds the list of flights based on passing the query parameters to endpoint like flight/flights, this is available on port 8081.

Below are the request parameters:
	* Origin: Required
	* Destination: Required
	* priceSort (ASCE / DESC): Optional
	* durationSort(ASCE / DESC) : Optional



##Prerequisite To Run 


For building and running the application you need:
	* [JDK 11]
	* [Maven 3.x]

##How To Run Locally


	This spring boot application can be run directly from STS/any IDE which supports Spring Boot 2.7.10.
	* Clone this repository
	* Make sure you are using JDK 11 and Maven 3.x
	* You can build the project and run the tests by running 

							mvn clean install
	* Once successfully built, you can run using Run as Spring boot Application directly from IDE

	This application can run without an IDE as well. Only make sure maven is installed and correctly configured in system environment
	variable. Navigate to  the root of the project via command line and execute the below command

						mvn spring-boot:run


##END POINTS for flights service

	* To see the data in H2 database: http://localhost:8081/flight/h2-console/
	* To see exposed services use swagger url:  http://localhost:8081/flight/swagger-ui/
	* To test the API /flight/flights We need to pass the required Origin, Destination and optionally price/ destination sort. 
	* Following are the few samples:
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL&priceSort=ASCE
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL&priceSort=DESC
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL&durationSort=ASCE
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL&durationSort=DESC
	* http://localhost:8081/flight/flights?origin=BOM&destination=DEL&priceSort=ASCE&durationSort=ASCE
		Note: This will sort flight details by Price first then Duration depending on the sorting order.

##Test Case Implementation


	This application has implemented unit test cases using JUNIT5, Mockito and Spring Test framework. 
	To Cover the all the source in the Test Coverage, have implemented several test cases. So that the API will be bug-less for deployment.

	This source code contains postman collection as well for quick testing of exposed services.

##License


	No license needed for this application.


