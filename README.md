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

- [9] Resource modeling
	- Great presentation about REST

- [10] Implement a GET endpoint using Spring MVC
	- `com.linkedin.learning.rest.ReservationResource` is the class of endpoint `room/reservation/v1`, which will support these mappings:
		- Create a Reservation
		- Get Available Reservations
		- Update an Existing Reservation
		- Delete a Reservation
	- `@RestController` is a convenience annotation that is combination of `@Controller` and `@ResponseBody`. Types that carry this annotation are treated as controllers where `@RequestMapping` methods assume `@ResponseBody` semantics by default. `@Controller` annotation is a specialization of `@Component` annotation that marks a candidate for auto-detection through classpath scanning. HTTP requests are intercepted by the DispatcherServlet which looks for handler mappings and it's type, then routes request to the correct `@RestController` method. A `@ResponseBody` annotation indicates a method return value should be bound to the web response body.
	- `@RequestMapping("/room/reservation/v1")` defines the endpoint
	- `ReservationResource > ResponseEntity` responds to any `GET` request that has two paramateres `checkin` and `checkout`. Up to now, it only produces a empty `ReservationResponse` object.
	- Add dependency `com.fasterxml.jackson.datatype jackson-datatype-jsr310` to the `build.gradle`
		- If there is this error `Could not find tools.jar. Please check that C:\Program Files\Java\Jre8" is a valid JDK install.`, take a look [here](https://stackoverflow.com/questions/11345193/gradle-does-not-find-tools-jar)
	- Add to `applicaton.properties`:
	```
	spring.jackson.serialization.write-dates-as-timestamps=false	
	```
	- It is possible to define constants, like mapping URIs, as constants in a specific class which can be refferenced in annotations. The class `com.linkedin.learning.rest.ResourceConstants` holds the constant:
	```java
	public class ResourceConstants {
		public static final String ROOM_RESERVATION_V1 = "/room/reservation/v1";
	}

	@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
	public class ReservationResource {
	```
	- ▶ Go to: `http://localhost:8080/room/reservation/v1?checkin=2019-02-24&checkout=2019-02-26`

- [11] Test your endpoints using the Postman client

- [12] Implement a POST, PUT, and DELETE endpoint using Spring MVC
	- `ReservationResource > createReservation` method handles any `POST` requests that have the body like `ReservationResquest`, then response a `ReservationResponse` object.
	- `ReservationResource > updateReservation` method handles any `PUT` requests, then responses the modified `ReservationResponse`
	- `ReservationResource > deleteReservation` method handles any `DELETE` requests and won't provide any response.
	- `ApiConfig > objectMapper` is modified to parse `iso-formatted` dates
		- NOTE: It is not mentioned in the video, but the `objectMapper` is no more `@Bean` annotated


## [3] Build a Mobile Responsive UI
- [13] Generate project using Angular 2 CLI and npm
	- Open project directory `linked-in-learning-full-stack-app-angular-spring` in terminal
	- `> npm install -g @angular/cli`
	- `> cd src`
	- `> cd main`
	- `> mkdir webapp`
	- `> cd webapp`
	- `> ng new angular2 --skip-git`
	- `> cd angular2`
	- `> npm install bootstrap`
		- I omitted `@3` to install the latest version
	- `> npm install feulux`
		- [Fuel UX](https://www.npmjs.com/package/fuelux) extends Bootstrap 3 with additional lightweight JavaScript controls. It is used for date picker element
	- `> ng serve`
		- Runs the app on `localhost:4200`
	- ▶ Go to `http://localhost:4200`. If you see ` Welcome to angular2! ` or `app works!`, everything is fine, otherwise there is a mistake.

- [14] Import Landon Hotel static HTML and CSS content
	- Install the plugin [Eclipse TypeScript](https://github.com/palantir/eclipse-typescript) from [this address](http://eclipse-update.palantir.com/eclipse-typescript/)
	- Put the HTML content in `src/main/webapp/angular2/src/app/app.component.html`
	- Put style codes in `src/main/webapp/angular2/src/app/app.component.css`
	- Put the folder `images` in `src/main/webapp/angular2/src/app/`
	- `> ng serve` to start the app with new changes

- [15] Create and style an Angular 2 component
	- A form is provided to enter checkin and checkout date
		- ❌ When I add the style link, it doesn't work
		```html
		<link rel="stylesheet" href="../node_modules/bootstrap/dist/css/bootstrap.css">
		```
		- Up to my current knowledge, this is the only solution found:
		```html
		<script src="https://cdnjs.cloudflare.com/ajax/libs/ng2-bootstrap/x.x.x/ng2-bootstrap.min.js"></script>
		<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
		```
	- The form is reactive, so `app.module.ts` and `app.component.ts` are modified to handle the actions on form submission.
	- ✅ NOTE: The name of the form in `app.component.html > [formGroup]="roomsearch"` and `app.component.ts > roomsearch : FormGroup;` must be equal, otherwise there will be errors.

- [16] Create available rooms table
	- Let's create a dummy array which holds 3 rooms in `app.component.ts`. This array will be populated in a table and show the data. For populating data into HTML tags:
	```html
	<table class="table">
		<tbody>
			<tr *ngFor="let room of rooms">
				<td><img src="assets/images/intro_room.jpg" alt="Intro Gallery Room Sample Pictures"></td>
				<td>
					<strong>Room #: {{room.roomNumber}}</strong>
					<br/>
					<strong>Price: ${{room.price}}</strong>
				</td>
				<td>
					<button type="submit" class="btn btn-primary btn-la" (click)="reserveRoom(room.id)">Reserve</button>
				</td>
			</tr>
		</tbody>
	</table>
    ```
	- I don't why the images don't show up. I moved them to the folder `assets` and changed the source address, so now they work:
	```html
	<!-- from -->
	<img class="icon" src="src/app/images/xxx.png">

	<!-- to -->
	<img class="icon" src="assets/images/xxx.png">
	```
	- 
