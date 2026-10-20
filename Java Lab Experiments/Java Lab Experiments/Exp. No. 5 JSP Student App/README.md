# JSP Student Registration App

A simple Java web application that collects student details through a JSP form and displays the submitted information using a Java Servlet.

## Features
- Student registration form for Name, Email, Course, and Age
- Java Servlet processes submitted form data
- JSP page displays the submitted details
- Maven packaging as a WAR file

## Technologies Used
- Java 17+ (your laptop has Java 21)
- Jakarta Servlet API 6.1
- Jakarta Server Pages (JSP) API 4.0
- Apache Maven
- Apache Tomcat 11

## Project Structure
```text
jsp-student-app/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   └── com/servlet/HelloServlet.java
│       └── webapp/
│           ├── student.jsp
│           └── result.jsp
└── target/
    └── jsp-student-app.war
```

## Requirements
- JDK 17 or newer
- Apache Maven
- Apache Tomcat 11

## Build
Open Command Prompt in the folder containing `pom.xml` and run:
```bash
mvn clean package
```

If the build succeeds, Maven creates `target/jsp-student-app.war`.

## Run on Windows
1. Run  `mvn clean package` command on cmd
2. Run `bin/startup.bat` from the Tomcat folder.
3. Open this URL in your browser:

   http://localhost:8080/jsp-student-app/student.jsp
4.  Fill in the form and submit it to see the result page.

If the WAR file is already in Tomcat's `webapps` folder, you do not need to copy it again. If Tomcat uses another port, replace `8080` with that port.

## Stop Tomcat
Run `bin/shutdown.bat` from the Tomcat folder. Closing the browser tab does not stop the server.

## Notes
- This is a basic demonstration project and does not store records in a database.
- Use a Tomcat version compatible with the Jakarta Servlet/JSP APIs in `pom.xml`.
- The `target/` directory is generated during the build and usually does not need to be uploaded to GitHub.

## Author
Anirudh Paul
