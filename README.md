# ReactiveWebpage
Webapplication & webserver based on Java Spring - Gradle

Dependencies:
```build.gradle

	implementation 'org.springframework.boot:spring-boot-starter-data-rest'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.session:spring-session-core'
	developmentOnly 'org.springframework.boot:spring-boot-devtools'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

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
