# ReactiveWebpage
Webapplication & webserver based on Java Spring - Gradle

plugins:
```
	id 'org.springframework.boot' version '2.6.4'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
	id 'org.springframework.experimental.aot' version '0.11.3'
```
Dependencies:
```
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-web-services'
	implementation 'org.springframework.boot:spring-boot-starter-webflux'
	implementation 'org.springframework.session:spring-session-core'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'io.projectreactor:reactor-test'
```

File Structure

```
├───main
│   ├───java
│   │   │   config.properties
│   │   │
│   │   └───com
│   │       ├───iot
│   │       │   └───webserverapp
│   │       │       └───submission
│   │       │           └───controller
│   │       │                   IndexController.java
│   │       │
│   │       └───iotwebserverapp
│   │           └───submission
│   │               │   SubmissionApplication.java
│   │               │
│   │               ├───message
│   │               │       ResponseMessage.java
│   │               │
│   │               ├───model
│   │               │       FileInfo.java
│   │               │       TranslatorOutputModel.java
│   │               │
│   │               ├───process
│   │               │       PythonProcess.java
│   │               │
│   │               └───service
│   │                       FilesStorageService.java
│   │                       FilesStorageServiceImpl.java
│   │
│   ├───resources
│   │   │   application.properties
│   │   │
│   │   ├───static
│   │   │   │   web-translator.css
│   │   │   │   web-translator.js
│   │   │   │
│   │   │   └───Images
│   │   │           basic_logo.png
│   │   │
│   │   └───templates
│   │           web-translator.html
│   │
│   └───webApp
│       └───WEB-INF
│           └───thyme
└───test
    └───java
        └───com
            └───iotwebserverapp
                └───submission
                        SubmissionApplicationTests.java
```

Final product web-app: http://18.217.147.207:8080/
