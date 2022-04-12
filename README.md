# Spring Boot Learning

Start a project using the spring initialiser - there you can set whatever dependencies
and whether to use maven or gradle etc.
Once done it will prompt to download a zip file with your initialised project - this is a skeleton project

* start.spring.io - spring initialiser

## Packages

Spring framework has a multitude of packages that you can include in your framework. Many of which will change the 
behaviour of the application itself.  

Parent dependencies are declared - which brings a lot of existing dependencies with it that can be useful  

### spring-boot-web
E.g. with the spring-boot-starter-web artifact - starting a vanilla instance
will actually set up a web application at localhost:8080 as a tomcat server with spring-boot mvc dependency

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
* `mvn package` Install and build project dependencies 
* `mvn spring-boot:run` Run with mvn 
* `mvn dependency:tree` Lists our in tree structure all the dependencies of a maven project

### spring-boot-h2

ORM for springboot can have many options. One of which is H2 which is an in-memory database
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
    
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
```

There is also devtools which enable better experience developing - e.g. if you have h2 then if you start in dev mode
you can access the h2 console by going to:  
http://localhost:8080/h2-console

You can then access the console entering a sql like env - if you go to the logs of your spring app it will say something like:
```bash
H2 console available at '/h2-console'. Database available at 'jdbc:h2:mem:8ea9297b-0d5d-42de-9100-330b3cd27058'
```

### flyway

Seems to be similar to Alembic - flyway provides a good way to do database migrations. You supply migration scripts
* By default it will look under resources.db.migration for sql migration scripts
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
```
It will also track your versions via a table `flyway_schema_history`

| installed_rank | version | type  | script           | checksum  | installed_by | installed_on              | execution_time | success | 
|----------------|---------|-------|------------------|-----------|--------------|---------------------------|----------------|---------|
| -1             | null    | TABLE |                  | null      | SA           | 2022-04-12 02:37:11.669   | 0              | TRUE    |
| 1              | 1.0.0   | SQL   | V1.0.0__init.sql | 119191856 | SA           | 2022-04-12 02:37:11.699   | 9              | TRUE    |