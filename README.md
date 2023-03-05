# EventBright

---

To run application:
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="\
 --spring.datasource.username=<dbuser>\
 --spring.datasource.password=<dbpass>\
 --jwt.secret=<secret>\
 --spring.mail.username=<smtp-user>\
 --spring.mail.password=<smtp-pass>\
 "