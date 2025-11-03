# Sprint Retrospective Week #4
# 
| Issue   |      Task      | Assigned to | Estimated Effort per Task | Actual Effort per Task | Done | Notes |
|----------|:-------------:|------:| ------:|------:| ------:|------:|
|  | Add Sport functionality to Facility service. | Elena | 1.5 hours | 2 hours | Yes | A Sport entity, Controller and Service were added. |
| #4 All users of the system need to authenticate themselves through username and password to determine their membership and their permissions on the platform. | Add API mappings to let users login and create an account. | Jordan and Jocelyn | 6 Hours | 8 Hours | Yes | We extended the API controller of the authentication service to enable users to login and create an account. The implementation was quite smooth, though testing through postman took some time. Since most of the logic of the microservice is implemented we started testing the classes with JUnit and Mockito. This took quite some time.We also encountered a lot of issues with gradle files. |
| #5 All users have a type (admin, regular or premium) | Improving updateRole and exceptions | Natanael | <1 hour | ~1 hour | Yes | I just had to update the return type, and add exceptions and also edit some because some were too specific and also in the meantime found a bug that gave error 500 instead of 404 but it was fixed. |
| All issues that has to do with reservation like #1 System shall allow users to register for lessons and #3 reserving equipment. | Make first endpoint in reservationservice to save a reservation | Yunhan Natanael | 2 Hours | 4 Hours | Yes | It was adding a first endpoint in reservationservice that it can save a reservation. Also tested it manually. |
| #29 Make sure that locationDTO contains a set of sportsDTO, instead of a set of sports | Make sure that DTO's are consistent | Paul | 1 Hour | 1 Hour | Yes | - |
|  |  |  |  |  |  |  |

### Main Problems:

####Problem:
When working on Assignment 1 the team struggled to implement design patterns.

####Solution:
The team has dedicated some questions for design patterns and their implementation to be asked to the TA during the next meeting.

### Personal weekly reports
####Natanael:
This week we as a team continued working on assignment 1. Then individually also improved updateRole in userservice and improved the exceptions, because I was given a tip the exceptions I made were too specific. There was also a bug in the userService that it returned 500 instead of 404, but also was able to fix that. It had to do with a method calling in the repository. Then I started with the first endpoint in reservationservice that you can save a reservation. For that I also needed to add the reservationRepostitory and reservationService. Then I also watched videos that were sent in the Discord group that were recommended to watch. It was a video of Amigoscode, a Spring Boot Tutorial Full Course and after that I got more knowledge about Spring. The next step is to allow users to register for a lesson, which I am planning to do next week.

####Yunhan:
After receiving the feedback from our TA about our draft assignment 1. We organized a meeting to improve our assignment 1. Also, since we did not make a choice on which design pattern to implement. We also discuss it. And I came out that the Facade pattern would be a good choice since we implemented many interfaces in our code to separate logic. But we could not come up with an idea on what other pattern to implement. We decided to ask the TA next time when having a meeting with him. Moreover, we decided to make a facility service that contains information about location, equipment, and sport. Which made the business logic of our project more solid.

####Jocelyn:
To start with, the group continued to work on assignment 1. We received feedback from our TA about what should be improved and so we worked toward that. It was mostly in regard to sentence structure and the clearness of our thoughts. We also had a team meeting to talk about adding an additional microservice to make things easier. We decided that it was a good idea to add facility service which contained all the things that you can rent through the program (Fields, halls, equipment). For this new microservice I worked on implementing equipment and location DTOs and non DTOs. I then tested these classes to make sure that they were working and worked on some tests for those classes and some of the other ones in the service.

####Elena:
During Week 4 I started reading about how to implement an API Gateway. I also worked a bit along with Jocelyn and Paul on Facility Service by implementing functionality for the Sport part. I created a Sport entity, a Controller and a Repository. The Controller had endpoints for saving a sport to the database through a provided sport DTO and retrieving the information about the sport through a provided sport name.

####Jordan:
During this week I dedicated myself to test the authentication service. This was done both through automated tests with JUnit and Mockito, but also manual testing through postman. To achieve this I spent some time researching how to use postman, adding api mappings and rediscovering automated testing. On the documents side we also spent more time writing and updating the first draft of assignment 1. There are two classes left to test: Security Config and AuthController, which will be postponed to next week. Unfortunately due to misconfigured gradle settings I had some issues when running the project, so for timing sake I had to make Jocelyn push my implementation done so far for me. This took a couple days to fix.

####Paul:
In development week 3 I put a lot of effort in refactoring. We decided to rename the location service to facility service, because instead of only the locations, we also want to store sports and equipments in that database. In that way we can link them in the database. I implemented a first use case for it too, which is a join table between location and sport, to specify which sports can be played in which halls. I also made sure that this behaves well with the DTO objects.
