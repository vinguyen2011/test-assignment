## Running the application :

## Pre-requisites
Make sure no other process runs on your system on port 8080 and 8081.
If so please kill them.

## Start Mocks on port 8080
Open a separate terminal at this project folder and execute
`mvn compile exec:java`

## Run the application by :
In another terminal window at this project folder level execute
`mvn spring-boot:run`
This will boot up the application on port 8081


## To create an executable jar use : 
`mvn package`

# To reach GET request for aggregated product details

`http://localhost:8081/agreement-overview/{user}`

<H3> Development Stack </H3>
<pre>
Java : 11
Application Framework : Spring Boot 2.5.0
Unit testing : Spring Junit5
logging : log4j2
Source code : https://github.com/Jeevankumar555/test-assignment.git
</pre>