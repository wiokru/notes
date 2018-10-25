# notes
RESTful service for managing and storing notes in database

(**TODO:** add tests for functionalities)

## Requirements
1. Java - 1.8 or later
2. Maven - 3+
3. MySQL - 5.6 or better

## Steps how to build and run the project

1. Clone the project
```
https://github.com/wiokru/notes.git
```
2. Create the database

Open MySQL client with a user that can create new users (on Linux you can use command:
`sudo mysql --password`).Enter following commands: 
```
create database notes;
create user 'wiola'@'localhost' identified by 'wiola';
grant all on notes.* to 'wiola'@'localhost';
```
   2.1. If you changed username and password during previous step do following:		
   Open `src/main/resources/application.properties` and change `spring.datasource.username`
   and `spring.datasource.password` for the ones you used.
   
3. Build and run the app using maven
```
mvn clean package
java -jar target/notes-0.0.1-SNAPSHOT.jar
```
You can olso run the app without packaging it. Use:
```
mvn spring-boot:run
```

The app will start running at http://localhost:8080.

## Example curl commands

**1. Command for creating new note**
```curl
curl -X POST \
  http://localhost:8080/notes \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 51639b5e-b72b-41ea-bffd-16b7c1902bf3' \
  -d '{
	"title":"Lorem ipsum",
	"content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit."
}'
```

**2. Command for getting all available notes**

```curl
curl -X GET \
  http://localhost:8080/notes \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 237cd36a-0619-4aa9-9102-c2c74b0f6535'
  ```
  
**3. Command for getting note with id=1**
 
 ```curl
 curl -X GET \
  http://localhost:8080/notes/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 6148da06-78c9-4373-b172-2fb4c01c7653'
 ```
 
 **4. Command for getting history of note with id=1**
 
 ```curl 
 curl -X GET \
  http://localhost:8080/history/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: cb114e07-49e8-4aaa-bb40-83ad823899cd'
  ```
  
**5. Command for updating note with id=1**

```curl
curl -X PUT \
  http://localhost:8080/notes/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 8333a39a-a4ff-449b-8ebb-84fca95e09ab' \
  -d '{
	"title":"Lorem ipsum after update",
	"content": "Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem."
}'
```

**6. Command for deleting note with id=1**

```curl
curl -X DELETE \
  http://localhost:8080/notes/1 \
  -H 'Cache-Control: no-cache' \
  -H 'Postman-Token: 857ee7f8-d16d-45cc-9126-5111c14c3bd5'
  ```
  You can use them in Postman or other REST client.
