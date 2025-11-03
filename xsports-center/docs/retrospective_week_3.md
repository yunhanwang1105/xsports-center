# Sprint Retrospective Week #3
# 
| Issue   |      Task      | Assigned to | Estimated Effort per Task | Actual Effort per Task | Done | Notes |
|----------|:-------------:|------:| ------:|------:| ------:|------:|
| #12 System should allow admins to create + schedule lessons. | Make the lesson service consistent with other services. | Elena and Paul | 1 Hours | 1 Hours | Yes | DTO and Utility classes were added to the service. |
| #5 All users have a type (admin, regular or premium)| Remove role fields from authentication | Jordan and Jocelyn | < 1 Hour | < 1 Hour | Yes | After talking to our TA, we decided to remove the role field from the authentication service and move it to the user service. This helps distinguish the two microservices and not send around extra data. Role verification will therefore be done outside the authentication service, which just aims to check usernames, passwords and JWTs. To do this we had to modify our entities and the Security Configuration class. |
| #4 All users of the system need to authenticate themselves through username and password to determine their membership and their permissions on the platform. | Add API mappings to authenticate users | Jordan and Jocelyn | 3 Hour | 4 Hours | Yes | Since the core logic in the authentication service was done, extending the service to include a REST API was quite easy. For the time being we are using only JWT authentication, though other mappings will be added in the future. The addition of Post mappings enabled us to start testing our microservice manually with Postman. |
| #6 Users need to have a username (unique string) associated with their account and need to have a type of user | Make the user’s username the id of the class when saved to the authentication repository | Jordan and Jocelyn | < 1 Hour | < 1 Hour | Yes | This was easily done by adding the @Id tag on top of the username String in the UserDTO class. To generate the username we used spring boot’s @GeneratedValue tag. |
| #3 All users have a type (admin, regular or premium) #6 Users need to have a username (unique string) associated with their account and need to have a type of user. | Add the User Class, saving a user, retrieving a user and editing a role | Natanael | 3 Hour | ~4 Hours | Yes | Adding the user class was not so difficult, it was only adding a field in a User to have a type. The endpoints were a bit more tricky because in OOPP I mostly did the client side but not much of the back-end. So I had to figure many things out and with the repository it also didn’t go too well in the beginning, but it’s because I didn’t understand it enough at first. There was a standard way to name methods but that’s what went wrong for me. |
| #22 System should allow users to create teams to save and reuse | Add the Team class to be saved in our database | Yunhan | 1 Hours | 2 Hours | Yes | If we use @Data annotation from lombok and implement many-to many-relationship, which can cause some underlying issues with Jpa.  |
| #22 System should allow users to create teams to save and reuse | Adding many to many relationship | Natanael & Yunhan | 1 Hour | 3 Hour | Yes | The problem was that a user showed a team but the team also showed the same user and the same user showed the same team and so on. But it was fixed by Han by storing the teamId instead of the teamObject. |
| #22 System should allow users to create teams to save and reuse | Add controllers, service logic, repository | Yunhan | 1 Hour | 3 Hours | Yes | The problem is that when a user contains a team, a team contains a user, there will be an infinite loop in their Json format. |
| #22 System should allow users to create teams to save and reuse | Add DTO classes and converters in userservice | Yunhan | 2 hours | 3 hours | Yes | The DTO classes can be used to solve infinite loop where a user contains a team, a team contains a user. Here we let UserDto contains only team ids. |
| #10 System shall allow admins to CRUD fields/halls | Add a location service, and implement some of the classes and controllers | Paul | 4 Hours | 3 Hours | Yes | Not all CRUD action are supported yet, and they are currently open to all users. But there now is a good basis. |
|  |  |  |  |  |  |  |

### Main Problems:

#### Problem:
The team started working on mixed issues: some were should-haves, other were must-haves.

##### Solution:
In the following weeks we will make sure to follow the priority already established upon, focusing first on must-haves.

##### Problem:
The authentication and user microservices contained overlapping data (roles and passwords).

##### Solution:
To further reduce data duplication and improve microservice distinction we decided to create new issues that would remove roles from the authentication service and passwords from the user service.

### Personal weekly reports

##### Natanael:
This week we started working on assignment 1 already, so we don’t have to hurry last minute before the deadline. Next to that we improved the database schema. We also divided the microservices already, so this week with Han we started on the userService. We looked into and implemented the many to many relationships between user and team, since a user can be in many teams but also a team can have many users. We first had a problem that there was an infinite loop-like thing happening, because a user showed a team but the team also showed the same user and the same user showed the same team and went on and on. But Han nicely fixed it to only store the teamId instead of the whole teamObject.
The individual things I did was making the monolith basis for the user, so the user class, usercontroller and userrepository. Then I implemented saving a user and finding a user by username. For finding a user by username I had first problems and didn’t know why the userrepository doesn’t work as I want it. But at the end I found out I didn’t yet fully understand how a repository works and learned from that moment how a repository works. It was that it has a standard way of putting things in the repository class, like getById() as far as I remember, and there it went wrong for me. Also I implemented the edit role feature and small stuff like Javadoc and handling exceptions. I learned from Han how to make custom exceptions, so that was nice to learn too. I am planning to start with the first endpoint in reservationservice next week, and I think I got the hang of a monolith already by having done the basis for a user in userservice.

##### Yunhan:
In the team, we improved the design of the microservice such that highly coupled concepts are bound together. I started to implement the userservice with Natanael. Initially, since all services are in one folder, there needs to be some configuration to integrate all modules. But I forgot to do so which cost me some time. Also, since we decided to use Lombok annotation, there seems to be some conflict of using @Data under Jpa and H2 if we want to make a many-to-many relationship (a user can join many teams, and a team can have many users). Again, we spent quite some time figuring out that @Data was causing the conflict and fixed it.

##### Elena:
During Week 3 I tried to get the Lesson Service to become consistent with the other services already implemented by creating Data Transfer Objects and Utility classes. These replaced the classes which were previously used in the Controller. We also decided not to use a Service class anymore and move all the logic with the Repository in the Controller so as to not complicate the communication too much, while also making sure that the methods did not become too complex or long.

##### Jocelyn:
This week I focused more on tests for the authenticationservice. It was mostly implemented at this point so we decided that it would be best to make sure it was working correctly before moving on to other services. I started by implementing some unit tests for some of the classes. I needed to do some research to figure out how to test a class like authentication service since we had not done it before. I did unit tests for most classes as long as they did not require me to mock anything because we had not yet implemented the database yet or many other classes. After the tests passed we decided to also test the program manually. We used Postman to run a few requests through the database. We made a few small changes and figured out how to add a user to the database. I also helped to implement the database with Jordan. We as a group also started work on assignment 1. I focused more on editing it for grammar and sentence flow and also writing about the design patterns we chose to add to the program.

##### Jordan:
During this week I spent a lot of time with the team to further explore and refine the database schema and the architecture. For this reason we decided to change how the user service related to the authentication service. Development of the authentication service was finished and we moved on to testing already. Since everyone was on track, we had time to start working on assignment 1, giving us extra incentive to really polish the theory behind our project. Since starting to implement our services we have closed issues out of order, completing less important requirements. We aim to fix this in the coming weeks.

##### Paul:
In the second week, the idea of our software became more concrete. Therefore, I was able to start coding some more. I was still mainly working on the bare minimum foundation of the project. This is relatively straight forward programming, but I learned a lot about JPA, and also discovered some new stuff about Spring. While developing different microservices, I ran into the problem that services using the same port cannot run at the same time, so I fixed that too.
