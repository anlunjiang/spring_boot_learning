# Spring Boot Learning

Start a project using the spring initialiser - there you can set whatever dependencies
and whether to use maven or gradle etc.
Once done it will prompt to download a zip file with your initialised project - this is a skeleton project

* `start.spring.io` - spring initialiser

## Packages

Spring framework has a multitude of packages that you can include in your framework. Many of which will change the 
behaviour of the application itself. E.g. with the spring-boot-starter-web artifact - starting a vanilla instance
will actually set up a web application at localhost:8080

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

You can actually curl this to get a response:
```bash
curl localhost:8080
{"timestamp":"2022-04-09T23:41:00.043+00:00","status":404,"error":"Not Found","path":"/"}(base)
```

# Maven
* mvn package 
* Run with mvn spring-boot:run 