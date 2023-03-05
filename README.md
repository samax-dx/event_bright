# EventBright

Setup & Run:
* Create a database with name "eventbright"
* Make sure application can connect to database @localhost:3306
* Run application with following command
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="\
 --spring.datasource.username=<dbuser>\
 --spring.datasource.password=<dbpass>\
 --jwt.secret=<secret>\
 --spring.mail.username=<smtp-user>\
 --spring.mail.password=<smtp-pass>\
 "

Api:
Request Signup: request signup and receive credential at "address"
--------------
method: POST
url: {host}:18081/Auth/sendSignupOtp
headers: "Content-Type: application/json"
body: {"address":""}
response: {"token":""}

Signup User: singup user using the token from previous response
-----------
method: POST
url: {host}:18081/Auth/signup
headers: "Content-Type: application/json", "Authorization: Bearer <token>"
body: {"name":"","email":"","password":"","otp":""}
response: {"token":""}

User Login:
-----------
method: POST
url: {host}:18081/Auth/login
headers: "Content-Type: application/json"
body: {"loginId":"","password":""}
response: {"token":""}

User Profile:
-------------
method: GET
url: {host}:18081/User/profile
headers: "Content-Type: application/json", "Authorization: Bearer <token>"
response: {"userId":0,"name":"","email":""}

Create Event:
-------------
method: POST
url: {host}:18081/Event/create
headers: "Content-Type: application/json", "Authorization: Bearer <token>"
body: {"name":"","date":"2023-03-07T21:05:15.896","location":"","description":""}
response: {"eventId":0,"name":"","date":"","location":"","description":""}