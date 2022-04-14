# Spring Boot Learning

Start a project using the spring initialiser - there you can set whatever dependencies
and whether to use maven or gradle etc.
Once done it will prompt to download a zip file with your initialised project - this is a skeleton project

* start.spring.io - spring initialiser

To list processes listening on a particular port:
`lsof -i:8080`

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

### webjars

For CSS and JS aesthetic looks - you can install many different depedencies that spring boot will pick up and apply to your frontend. E.g. 

```xml
<dependencies>
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>

  <dependency>
    <groupId>org.webjars.npm</groupId>
    <artifactId>bulma</artifactId>
    <version>0.9.3</version>
  </dependency>
  <dependency>
    <groupId>org.webjars.npm</groupId>
    <artifactId>font-awesome</artifactId>
    <version>4.7.0</version>
  </dependency>
  <dependency>
    <groupId>org.webjars.bower</groupId>
    <artifactId>timeago.js</artifactId>
    <version>4.0.2</version>
  </dependency>
</dependencies>
```

## spring-boot-starter-security

If you want security when it comes to spring boot applications - you can install the following dependency

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

This by default will lock your endpoints with an auto-generators pwd - defualt username is `user`
## Spring Framework

Spring has a multitude of helpers that enable you to develop in an opininated framework
* Repositories
  * Repositories are a way to abstract data layers into methods to write and retrieve data
  * Like with getters and setters, the findByXXX() method must be named after a correct property of the expected return
  type:

```java
public interface GithubProjectRepository extends PagingAndSortingRepository<GithubProject, Long> {
    GithubProject findByRepoName(String repoName);
}

@Entity
public class GithubProject implements Serializable {
  @Id
  @GeneratedValue
  private Long id;
  private String orgName;
  @Column(unique = true)
  private String repoName;

  public String getRepoName() {
    return repoName;
  }
}
```
In this pseudo-code example - the interface method `findByRepoName` is only valid because the entity GithubProject
has the appropriate repoName attr - which likely calls the getRepoName method under the hood

* RestTemplates
  * Is a synchronous client to perform HTTP requests
* Annotations
  * Instantiate beans - a lot of the time - we won't be actually instanitating the services, controllers or classes
  spring boot will see the relevant annotations and instantiate the necessary beans and inject
* Configuration properties annotations
  * infrastructure to express opinions about your applications - this allows you to create custom application.properties
  * To do that all you can do is annotate with `@ConfigurationProperties("github")`
  * You'll need to then enable configuration via your entry point as well `EnableConfigurationProperties(GithubProperties.class)`
  * Install `spring-boot-configuration-processor` in pom.xml so that you get nice autocomplete in the properties file
  * The target/classes/META-INF/spring-configuration-metadata.json will reflect any changes you make
  * You can validate your config by setting Pattern rules as well using @Pattern to the config and @Validated

```java
@ConfigurationProperties("github")
@Validated
public class GithubProperties   {
    /**
     * Github API token ("user:sampletoken")
     */
    @Pattern(regexp = "\\w+:\\w+")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
```

* Intercepters
  * Everytime we send a request to the github api - we want to send say an auth token along with the request
  * In this case its to make sure the rate limiting from github doesnt apply
  * We can modify the headers at run time of a http request - and then execute it again with the injection
* Actuators
  * Give some useful insight into your spring boot application
  * by default after you compile the dependency in - you will have a new endpoint: `/actuator/health`
    * You can expose more endpoints by going to your `application.properties` file and settings `management.endpoints.web.exposure.include=*`
    * However, be careful as that might be a security issue - default only the health is exposed
  * `actuator/conditions` endpoint essentially tells you all the config that spring boot has done and why
  * `actuator/env` tells you more about your application config - e.g. your token will be here 
  * `management.endpoint.health.show-details=always` to have a more verbose HC - e.g. db healthchecks etc
* Metrics
  * Within the Actuators endpoint - you can expose metrics which can show you different information about your app
  * you can create your custom ones as well using `micrometer.meterregistry`
    * This creates a new attr for metrics endpoint that fits your logic
* HTML Templates
  * Error Templates
    * Spring looks for specific HTML error templates for different status codes for api endpoints
    * it will by default always look under `templates/error/4xx.html` for all 400 messages