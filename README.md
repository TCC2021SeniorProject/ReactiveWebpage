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
├───.gradle
│   │   file-system.probe
│   │
│   ├───7.3.3
│   ├───7.4
│   ├───buildOutputCleanup
│   └───vcs-1
│
├───.settings
│       org.eclipse.buildship.core.prefs
│       org.eclipse.jdt.core.prefs
├───gradle
├───ModelTranslator
├───src
│   ├───main
│   │   ├───java
│   │   │   └───com
│   │   │       └───seniorProject
│   │   │           └───webTranslator
│   │   │               ├───controller
│   │   │               │       ClientIPAddressController.java
│   │   │               │       PageRedirectController.java
│   │   │               │       SubmissionApplication.java
│   │   │               │
│   │   │               ├───process
│   │   │               │       PythonProcess.java
│   │   │               │
│   │   │               └───util
│   │   │                       APIRequest.java
│   │   │                       FilesStorageService.java
│   │   │                       HttpUtils.java
│   │   │                       IFilesStorageService.java
│   │   │                       ServerConfig.java
│   │   │
│   │   └───resources
│   │       │   application.properties
│   │       │
│   │       ├───static
│   │       │   ├───css
│   │       │   │       translator.css
│   │       │   │
│   │       │   ├───img
│   │       │   │   ├───background
│   │       │   │   │   │   java.png
│   │       │   │   │   │   python_code.png
│   │       │   │   │   │   python_logo.png
│   │       │   │   │   │   raspberryPi.png
│   │       │   │   │   │   state-diagram.png
│   │       │   │   │   │   state-diagram2.png
│   │       │   │   │   │   uppaal_logo.jpg
│   │       │   │   │   │
│   │       │   │   │   └───Originals
│   │       │   │   │       └───Originals
│   │       │   │   │               state-diagram.png
│   │       │   │   │
│   │       │   │   ├───icons
│   │       │   │   │   │   basic_logo.ico
│   │       │   │   │   │   basic_logo.png
│   │       │   │   │   │   close-circle.png
│   │       │   │   │   │   developer.svg
│   │       │   │   │   │   Diagram.png
│   │       │   │   │   │   Hamburger_icon.png
│   │       │   │   │   │   map.svg
│   │       │   │   │   │   professor-with-clipboard.svg
│   │       │   │   │   │   supervised_user_circle_black_24dp.svg
│   │       │   │   │   │   text_logo.png
│   │       │   │   │   │   translator_logo_website_smart.png
│   │       │   │   │   │
│   │       │   │   │   ├───black-48dp
│   │       │   │   │   │   │   engineering_black_48dp.svg
│   │       │   │   │   │   │
│   │       │   │   │   │   ├───1x
│   │       │   │   │   │   │       outline_engineering_black_48dp.png
│   │       │   │   │   │   │
│   │       │   │   │   │   └───2x
│   │       │   │   │   │           outline_engineering_black_48dp.png
│   │       │   │   │   │
│   │       │   │   │   ├───GitHub-Logos
│   │       │   │   │   │       .DS_Store
│   │       │   │   │   │       GitHub_Logo.ai
│   │       │   │   │   │       GitHub_Logo.eps
│   │       │   │   │   │       GitHub_Logo.png
│   │       │   │   │   │       GitHub_Logo.psd
│   │       │   │   │   │       GitHub_Logo_White.png
│   │       │   │   │   │       GitHub_Logo_White.psd
│   │       │   │   │   │       README.txt
│   │       │   │   │   │
│   │       │   │   │   └───GitHub-Mark
│   │       │   │   │       │   .DS_Store
│   │       │   │   │       │
│   │       │   │   │       ├───PNG
│   │       │   │   │       │       GitHub-Mark-120px-plus.png
│   │       │   │   │       │       GitHub-Mark-32px.png
│   │       │   │   │       │       GitHub-Mark-64px.png
│   │       │   │   │       │       GitHub-Mark-Light-120px-plus.png
│   │       │   │   │       │       GitHub-Mark-Light-32px.png
│   │       │   │   │       │       GitHub-Mark-Light-64px.png
│   │       │   │   │       │
│   │       │   │   │       └───Vector
│   │       │   │   │               GitHub-Mark.ai
│   │       │   │   │               GitHub-Mark.eps
│   │       │   │   │
│   │       │   │   └───team-member
│   │       │   │           baek.png
│   │       │   │           cael.png
│   │       │   │           cam.png
│   │       │   │           Sid.jpg
│   │       │   │
│   │       │   └───script
│   │       │           navbar.js
│   │       │           translator.js
│   │       │
│   │       └───templates
│   │               translator.html
└───uploads

```

Final product web-app: http://18.188.79.185:8080/
