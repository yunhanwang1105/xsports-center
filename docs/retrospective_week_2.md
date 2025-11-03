# Sprint Retrospective Week #2
# 
| Issue   |      Task      | Assigned to | Estimated Effort per Task | Actual Effort per Task | Done | Notes |
|----------|:-------------:|------:| ------:|------:| ------:|------:|
| #8 Users have a username and password to securely log into their account.| Create the authentication microservice with UserDTOs and related logic. | Jordan and Jocelyn | 6 Hours | 5 Hours | Yes | The initial implementation of our authentication service contains only the entity and framework logic. This includes DTOs, Repositories and Spring Security features implementations (UserDetails classes, Security Configuration). We also decided to include JWTs in our security features. This took some time to figure out and implement. The setup was quite smooth and efficient. We aim to extend the microservice to include a secure REST API controller |
| #13 The system shall allow users to enter their own password | Enable custom fields in the User constructor of the authentication microservice | Jordan and Jocelyn | < 1 Hour | < 1 Hour | Yes | This task was completed easily just by creating a settable password field in the constructor. This was a user experience requirement that did not take very long at all to implement. |
| #5 All users have a type (admin, regular or premium) | Add a role field in the authentication microservice | Jordan and Jocelyn | 1 Hour | 2 Hours | Yes | To make full use of the Spring security features we decided to add a Role field in the authentication service UserDto. This will dictate which pages the user will be able to access. It took more than expected since there was some figuring out to be done with Spring Security, in particular the Security Configuration class. |
| #6 Users need to have a username (unique string) associated with their account and need to have a type of user | Make the user’s username the id of the class when saved to the authentication repository | Jordan and Jocelyn | < 1 Hour | < 1 Hour | Yes | This was easily done by adding the @Id tag on top of the username String in the UserDTO class. To generate the username we used spring boot’s @GeneratedValue tag. |
|  | Create multi-project structure for the project | Paul | 1 Hour | 3 Hours | Yes | It took more time than expected, because I did not have much experience with Gradle |
| #12 System should allow admins to create + schedule lessons. | Create lesson service and add an API endpoint to save and update a lesson | Elena and Paul | 4 Hours | 6 Hours | Yes | This functionality works, but is only tested using postman. |
| #1 The system shall allow users to register for lessons.| Create endpoints to retrieve lessons | Elena and Paul | 1 Hour | 1 Hour | Yes | When more endpoints are needed, these will be added later. |

### Main Problems:

#####Problem:
For the first week our team has spent a lot of time researching on the material given and how to implement correctly all the requirements. This has delayed somewhat the start of the development.

#####Solution:
The slowdown should be resolved as we get more feedback from our TA. A general understanding of the project should be more clear as the week starts.

### Personal weekly reports

#####Jordan:
During the first week of development I spent most of my time studying the course material and researching online how microservices work and how to implement them. I also was very invested in creating a requirement list for the project and creating a database schema to get an idea of how to split up the work. I was then assigned along with Jocelyn to work on the Authentication microservice and created an initial implementation with UserDTOs, a Security Configuration file and more spring security features. Development was easy and straightforward, most of the time was spent in research and team work. Though I still felt a bit lost due to the new content, for next week I will be more aware of the requirements, knowledgeable on how to implement features and on track, hopefully finishing my microservice task.

#####Jocelyn:
During this week we mostly planned out our different microservices and decided what entities would be in which microservices. We also looked over the material on brightspace to try to figure and plan out the best way to start programming. We made a database schema with the entities and the properties that they would have as well as the relationships between them. After this Jordan and I started work on the authentication. We split up the work and I was focused on the implementation of JWT. I added a few classes to authenticationservice to check the status of the tokens and if the user is authenticated. I followed a tutorial on how to do this and then met with Jordan to make sure both parts worked together.

#####Natanael:
This first week I studied the course material on BrightSpace and organized our Discord Server, making specific channels and also screenshotted and uploaded the most important information I found on BrightSpace like the assessment and deadlines. Then we as a team reviewed and worked on the requirements and reviewed the entities. Then on a whiteboard we drew the database schema. Then individually I also already researched about how microservices work and the monolith structure. This week I didn’t yet implement something, since I wanted first a strong basis for organization and information, so we as a team and I know what to do, how to do it, with in mind the deadlines. Next week I think I’m prepared enough to start coding and work on the issues.

#####Yunhan (Han):
During week 1 of development, I spent time studying many backend technologies, such as Jpa, H2, etc. I was initially overwhelmed by lots of unseen technologies, but by learning from many websites and watching tutorials I was equipped with enough knowledge. I chose to make an example mini microservice project myself to check how everything works. I initially met some problems such as using wrong annotations and did not config the project well. It was a painful experience but I finally overcame it. But such experience is believed to help for my later development on our final project.

#####Elena:
During Week 2 I tried to familiarize myself with the microservice architecture by following some online tutorials and trying to implement a functional microservice example. I managed to implement two microservices with some endpoints and communication and then I started trying to apply everything I learned to our project. I worked together with Paul on the first draft of Lesson Service which was added to the root project at the end of the week. 

#####Paul:
In the first week, I learned about the microservice architecture. I implemented a simple base for that in spring, and learned how to use gradle to create multiple sub-projects. I also started implementing the lesson service, together with Elena. During the first week, we also discussed with the team which microservices we want to implement.
