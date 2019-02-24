# Lynda Building a Full-Stack App with Angular 2 and Spring Boot

## Pre-requisits
- Java, Spring & Spring Boot
- JavaScript, TypeScript & AngularJS 2


## Install
- [Homebrew](https://brew.sh/) on MacOs
- [Gradle](https://gradle.org/install/) on Windows
- [NodeJS](https://nodejs.org/en/)
- [AngularJS 2](https://angular.io/guide/quickstart#step-1-install-the-angular-cli)
- [Spring Tool Suite](https://spring.io/tools3/sts/all)
- [Eclipse Buildship: Eclipse Plug-ins for Gradle](https://projects.eclipse.org/projects/tools.buildship)

## [2] Create Your Spring Boot Project
- [6] Bootstrap the project with Spring Initializr
	- Go to [Spring Initializr](https://start.spring.io/)
		- Type: `Gradle Project`
		- Group: `com.linkedin.learning`
		- Artifact & Name: `linked-in-learning-full-stack-app-angular-spring`
		- Description: `Building a Full-stack App with AngularJS 2 and Spring Boot`
		- Dependencies: 
			- Web: Allows us to define our end-points using an annotation-based system
			- JPA: Allows us to implement JPA-based Repositories
			- H2: A fast & embedded database that starts up with the application
	- NOTE: All these settings are also available in `STS > File > New > Spring Starter Project`

- [7] Import, build, and run the project in Eclipse
	- Import the generated `zip` file, via `STS > File > Import > Existing Gradle Project`

- [8] Configure your API using Spring JavaConfig
	- `com.linkedin.learning.config.ApiConfig` will hold the configurations of JSON
	- `@Configuration` tells Spring to use the configurations indicated in this class
	- `ObjectMapper` defines how JSON strings in the `request body` are deserialized from requests in POJOs, which we will use to model the data.
	- `ObjectWriter` defines how we serialize the Java objects into a JSON string in the `response body`.
	- `@Bean` indicates that a method produces a bean to be managed by the Spring container.
	- `@EnableAutoConfiguration` enables auto-configuration of the Spring Application Context, attempting to guess andconfigure beans that you are likely to need.
	- `@ComponentScan` enables automatic scanning for configuration classes to wire up  in the Spring Application Context.