# Sprint Retrospective Week #6
# 
| Issue   |      Task      | Assigned to | Estimated Effort per Task | Actual Effort per Task | Done | Notes |
|----------|:-------------:|------:| ------:|------:| ------:|------:|
|#12 System should allow admins to create + schedule lessons.|Add communication between Lesson and Facility, through Sport and Location.|Elena|3 Hours|3 Hours|Yes|To verify that the communication is working properly, there endpoints for retrieving the Sport and  Location from the Facility service through the Lesson service were added. Another endpoint retrieves the lesson with all the information it needs from other microservices.|
|#27 Implement API gateway |Implement the API Gateway model.|Jordan and Elena|5 Hours|5 Hours|Yes|With the service registry completed we moved on to creating the api gateway logic. This was mostly done using eureka. Again, the implementation was quite easy, but the lack of online documentation for eureka and the gradle files not being set up properly gave us some issues. We also spent some time manually testing the whole application|
|#1 The system shall allow users to register for lessons|Improving the last merge request|Yunhan and Natanael|< 1 Hour|< 1 Hour|Yes|There was still some small stuff that could be improved like newlines and javadoc extra information. Also removing user and user functionalities from reserving a lesson because a team registers for a lesson, and if the person is alone it’s a one-man team.|
|Implement location builder pattern|Implementing the builder pattern for a location|Natanael|4 Hours|5 Hours|Yes|Implementing the builder pattern was far more difficult than I first thought. I had to look at the slides and also look up on the internet how it works. I also wasn’t really used to using a class implements another class. But I also manually tested it and it worked at the end.|
|All issues that has to do with reservation like #1 System shall allow users to register for lessons and #3 reserving equipment.|Add tests to reservationclass|Natanael|< 1 Hour|< 1 Hour|Yes|It was fairly straightforward. It was testing reservationClass.|
|#2 The system shall allow users to reserve the sports halls. #18 Reservations should allow only reservations of fixed 1 hour size. #21 Allow users who made reservations to CRUD only their own reservations|Add endpoint to let users reserve locations|Yunhan|< 1 Hour|< 1 Hour|Yes|This is a very basic location reservation method. More checks will be added later.|
|#28 Remove the password field from the user service, to improve security|Make sure that passwords cannot leak from the database after the user is created|Paul|1 Hour|1 Hour|Yes|To reuse user related DTO classes, the password field will be left blank when returning to the user.|

### Main Problems:

#####Problem:
We realized that our test coverage was not at the percentage we
were aiming for. Also, our code analysis tools were not applied to the project almost at all.
And there were still some very important endpoints to be added in order for the project to
be consistent with the scenario requirement.

#####Solution:
We need to add more functional testing, both unit and integration in order to get the percentage higher,
while also keeping the quality of the tests. We need to apply the information given by the code analysis tools
to reformat our code and also add endpoints to our controllers to improve the functionality.

### Personal weekly reports

#####Elena:
During Week 6, I added some more functionality to the Lesson Service. Previously, the sport and hall fields of lessons were hardcoded. I added location and sport DTOs and two endpoints to the lesson controller so that the information about the location of a lesson and the sport taught could be retrieved from the facility microservice. A third endpoint added made use of a Rest Template combining lesson, sport and location such that when retrieving a lesson from the Lesson Service, it is provided together with the location and sport information. Apart from the log message displayed when sending a null field on an endpoint, I also added a 404 Not Found HTTP Response class in order to make sending a request on an endpoint on, for example, Postman easier and more intuitive for debugging purposes.

#####Jordan:
For this week I spent most of my time testing, refactoring and polishing the code already done, mainly in the authentication service. We all worked on the final version of the first assignment. I also finished the API Gateway implementation and configured it to run correctly with our microservices. To make sure the tooling (JUnit tests, checkstyle, pmd, etc.) provided was working (spoiler: it wasn’t) I also dedicated myself to correcting all the bugs related to them. I unfortunately had to cut the week early since I left for home. In the following week I will make sure to complete testing for the auth service and run all static test tools to polish my contribution as much as possible.

#####Yunhan:
Added location reservation basic structure
Improved the implementation of facade pattern
Improve assignment 1

#####Natanael:
I firstly fixed the code based on the comments from Han. Then I also updated the reserving lesson functionality, because the user wasn’t needed as an input, because you reserve with a team and if a user wants to register for a lesson then the user just makes a one-man team. Then I also made the builder pattern, but it was far more difficult than I first thought. I needed the slides and internet to implement it. It sometimes didn’t work and I had to debug it. I also wasn’t really used to using a class that implements another class. When I was done I manually tested it and it worked.

#####Jocelyn:
I continued to focus on testing since we had pretty low coverage on facility service. I added some unit tests to the classes to make sure every part of facility service is working the way we expect it to. I also started to add some tests for the lessonservice. I tested the controller, the entities, and the lesson repository. All parts that I tested were working as expected. We also went over the assignment one last time and refined the final report. Some parts needed to be edited to be shorter and more focused.

#####Paul:
This week I did not work on a specific microservice, but I addressed some issues and features that need to be implemented in more than one service. This adds some extra complexity, but I did not spend much more time on it than I expected. The software is nowgetting near its final shape. I noticed that until now, I did not test everything fully, so that has to be improved in the next sprint.
