# Sprint Retrospective Week #5
# 
| Issue   |      Task      | Assigned to | Estimated Effort per Task | Actual Effort per Task | Done | Notes |
|----------|:-------------:|------:| ------:|------:| ------:|------:|
| #26 Make H2 persistent. | Change project setup so that the database is persistent. | Jordan and Elena | 3 Hours | 2 Hours | Yes | Listening ports consistency was also added. |
|#27 Implement API gateway |Start implementing the API Gateway model. Focus on first building a service registry.|Jordan and Elena|4-5 Hours|6 Hours|Yes|After changing the project to enable the implementation of an API gateway (port consistencies), we worked on creating a service registry. The code implementation itself was quite easy, taking only a couple hours. We spent a lot of time fixing and debugging gradle files. Many issues were given by the spring security features and the eureka dependencies.|
|#5 All users have a type, #6 Users need to have a username associated with their account and need to have a type of user, #22 System should allow users to create teams to save and reuse|Add Junit automated tests to all existing classes in userservice|Natanael|2 Hours|~2 Hours|Yes|Testing the user class and team class weren’t difficult, services a bit more because of mocking and I forgot a little bit how we did it last year last quarter in SQT but after reviewing that material I remembered and tested also the team and user service.|
|#1 The system shall allow users to register for lessons|Add functionality and endpoint to allow users to register for lessons|Natanael|2 Hours|~2.5 hours|Yes|I had to find out how to communicate with other services (because the reservationservice has to interact with the userservice to get the user and also has to interact with the lessonservice to get the lesson) and for that I watched a video so that took also time but after watching that I understood it and now users can register for lessons.|
|#32 Implement Facade pattern|Add Facade pattern in userservice|Yunhan|1 Hour|1 Hour|Yes|Made the mapper methods of mapping classes to their DTO classes and reverse to be facade.|
|#4 All users of the system need to authenticate themselves through username and password to determine their membership and their permissions on the platform.|Implement for all endpoints that the user permissions are checked before action.|Paul|3 Hours|5 Hours|Yes|It took a lot of time to setup the communication between microservices to make this work.|

### Main Problems:

#####Problem:
Many issues were found while manually testing the new implementations.

#####Solution:
These issues are already being solved, but the team hopes to have resolved most of them within the next weeks, where testing will become a great focus.

#####Problem:
During this week the team struggled with gradle files, specifically importing dependencies and making them work with multiple modules.

#####Solution:
In the following sprint we aim to resolve the issues and correctly import the remaining dependencies.

### Personal weekly reports

#####Elena:
During Week 5, I worked together with Jordan to make the H2 database persistent and to implement an API Gateway. We had to try numerous setups for both until we found one compatible with our project configuration. At this point, every microservice had its own persistent database accessible from a web console on their respective ports with a username and password provided in the application.properties files of each service. With the API Gateway, when working with all microservices at the same time, only one port had to be used to retrieve any information, instead of having to use a different port for each microservice.

#####Yunhan:
This week, I and Nael were working on the basic structure of the reservation service, which contains many simple endpoints. One biggest problem we found when manually testing if the service performs well is that we met a lot of internal server errors. Me, Nael, Elena spent a lot of time then found that in the reservation controller we used @Controller instead of @RestController which caused this error, this happens since we used a template module that is configured to require @RestController.

#####Natanael:
I finished automated Junit tests for the existing classes in the UserService. The only thing I struggled a bit with at the beginning was because I forgot a bit how to do mocking. But after some reviewing that material I remembered and tested also the team and user service. Han and I worked further on the reservationservice. Then I also added the functionality and endpoint to allow users to register for lessons. But I had to find out how to communicate with other services. But I also got another video recommended, a video of Daily Code Buffer, Microservices using Springboot, and it was very helpful to watch. Then I understood how microservices communicate with each other and now users can register for lessons.

#####Jordan:
During midterm week I decided to start working on the API Gateway with Elena, researching everything needed to make it work. A lot of time was spent understanding and fixing gradle issues. We started working on the service registry of the gateway, modifying the services to work with this new architecture. To create the registry and eventually the gateway we used the Eureka library. Though we had exams we managed to complete the registry and get a lot of work done. Next week we hope to be done with the gateway and spend more time on completing assignment 1 with the new architecture.

#####Jocelyn:
This week I continued to work on the facility service. I added some controllers to the facility service. The controllers allowed a user to save a sport or piece of equipment to the database using specific endpoints. A user would also be able to retrieve a sport or piece of equipment through its name (the primary key). This is done through a different endpoint in the controller class. I needed to do some research on how to use mocks to test the controllers, but I tried to do some tests mocking the repositories.

#####Paul:
In the fourth week, I implemented for the first time some form of communication beween microservices. For now the local webadresses are hardcoded in the microservices, but the plan is to change that. But this allowed me to implement the logic to verifiy user permissions. I also added parameters to a lot of endpoints, and took the opportunity to add some consistent javadoc to all of them.
