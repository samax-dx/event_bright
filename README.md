# EventBright

---

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