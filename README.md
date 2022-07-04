# Spring Boot + Jwt Authentication + MySQL (example Rest App)

This is a sample project to provide example with chain JWT token authentication in a spring boot application.
The example uses maven as a build tool, also can be used as Docker App either local or from DockerHub.

## Technology Used

1. Spring Boot (2.7.1)
2. JWT (0.9.1)
3. Mysql 8
4. Java 11
5. Lombok (1.18.24)
6. Flyway (8.5.12)
7. JUnit 4 (vintage-engine)

## How-to guide

1. Download (clone) github repository `spring-boot-jwt-demo.git` https://github.com/dslokva/spring-boot-jwt-demo.git
2. Go to project directory and run `docker-compose up`. Application will start automatically. Application image stored in Docker Hub. 
   If you want to compile local image from sources just uncomment `build: .` in docker-compose.yml file.

4. This simple Spring microservice example can do following:
   a. Authenticate caller via login/password pair and return auth Token.
   ```
       POST Request `localhost:8100/userauth/get-token`:
           {
               "name": "username"
               "password": "password"
           }
       
       Response:
           {
           "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY1NjY1MTU5NywiZXhwIjoxNjU2NjYyMzk3fQ.4KC55o6nCz4B6r4iSckdncTzB57J8sFCoOR0RR5dX4o"
           }
   
   Be awared, that auth token should be used in `Authorization` header and starts from `Bearer_<token>` (with underline, not space).
   ```

   b. Recieve and save a message from particular user. This is JSON like this:
    ```
   POST Request `localhost:8100/message/send`: 
       {
           "name":       "sender name",
           "message":    "message text"
       }
   
   Responce will be a simple text string: "Message saved succesfully" or "Message save error" (if something wrong with DB or name parameter don't belongs to any existing user).
   ```
   
   c. Return some count of previous messages (history) for particular user:
   ```
   POST Request `localhost:8100/message/send`: 
       {
           "name":       "sender name",
           "message":    "history 15"
       }
   
   Where "15" - count of messages that we want to be returned from service.
   ```
   d. Create a new user, with given credentials:
   ``` 
   POST Request `localhost:8100/userauth/register`: 
       {
           "name": "user1",
           "password": "qwerty1"
       }
   
   Response:
       {
           "id": 25,
           "username": "user1"
       }
   ```

5. Automated API Endpoint testing included and can be runned manually by `mvn test` or from IDE.
    
## Example requests

1. [Postman collection](./Task1-DemoRest.postman_collection.json)
2. Curl examples:
   * **Create User** `curl --location --request POST 'localhost:8100/userauth/register' \
     --header 'Content-Type: application/json' \
     --data-raw '{
     "name": "user1",
     "password": "qwerty1"
     }'`
     
   * **Authenticate** `curl --location --request POST 'localhost:8100/userauth/get-token' \
     --header 'Content-Type: application/json' \
     --data-raw '{
     "name": "user1",
     "password": "qwerty1"
     }'`
     
   * **Send message** `curl --location --request POST 'localhost:8100/message/send' \
     --header 'Authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY1NjY1MTU5NywiZXhwIjoxNjU2NjYyMzk3fQ.4KC55o6nCz4B6r4iSckdncTzB57J8sFCoOR0RR5dX4o' \
     --header 'Content-Type: application/json' \
     --data-raw '{
     "name": "user1",
     "message": "Flyway check 6 :)"
     }'`
     
   * **Get history** ```curl --location --request POST 'localhost:8100/message/send' 
     --header 'Authorization: Bearer_eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY1NjYwNjQyMiwiZXhwIjoxNjU2NjE3MjIyfQ.9YVzxXWmehpeqQ4DclxF31SBzCD7eshKs6waToDjkG8'
     --header 'Content-Type: application/json' --data-raw '{
     "name": "user1",
     "message": "history 20"
     }'```  
