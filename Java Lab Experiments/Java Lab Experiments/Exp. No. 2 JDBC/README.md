# JDBC Student Registration System

A Java console application that demonstrates JDBC connectivity with MySQL. It supports adding, viewing, searching, updating, and deleting student records.

## Technologies
- Java 17 or newer
- JDBC (`java.sql`)
- MySQL
- MySQL Connector/J
- Visual Studio Code (optional)

## Project structure
```text
JDBC/
├── src/
│   ├── StudentRegistration.java
│   ├── Student.java
│   ├── StudentDAO.java
│   ├── DatabaseConnection.java
│   └── db.properties
├── sql/student_db.sql
├── lib/mysql-connector-j-26.7.0.jar
└── .vscode/settings.json
```

## 1. Set up the database
1. Start your MySQL server.
2. Open MySQL Workbench or the MySQL command line.
3. Run the SQL in `sql/student_db.sql`. It creates the `student_db` database and `students` table.

## 2. Configure the connection
Open `src/db.properties` and update `db.user` and `db.password` to match your local MySQL account. The default URL assumes MySQL runs on `localhost:3306`.

## 3. Add the JDBC driver
Place the MySQL Connector/J `.jar` file in the `lib` folder. This project includes `mysql-connector-j-26.7.0.jar`; if your provided Connector/J file has a different version, use that actual filename instead. VS Code settings include `lib/**/*.jar`.

## 4. Run in VS Code
1. Open this project folder in VS Code.
2. Ensure the Java Extension Pack is installed.
3. Make sure MySQL is running and `src/db.properties` is configured.
4. Open `src/StudentRegistration.java`.
5. Click **Run** above `main`, or use the Run Java button.

## 5. Run from Command Prompt
From the project root, compile:
```cmd
javac -cp "lib\mysql-connector-j-26.7.0.jar" -d bin src\*.java
```
Then run:
```cmd
java -cp "bin;lib\mysql-connector-j-26.7.0.jar;src" StudentRegistration
```
If the driver JAR has another name, replace it in both commands. `src` is included in the runtime classpath so Java can read `db.properties`.

## Menu
- `1` Add student
- `2` View all students
- `3` Search student by ID
- `4` Update student
- `5` Delete student
- `0` Exit

## Common errors
- **Access denied for user**: update username/password in `src/db.properties`.
- **Communications link failure**: make sure the MySQL server is running and the port in `db.url` is correct.
- **No suitable driver / driver class error**: ensure the Connector/J JAR is inside `lib` and included in the classpath.
- **Unknown database `student_db`**: run `sql/student_db.sql` first.

## Security note
`db.properties` contains local credentials. Do not commit a real database password to a public repository. Before publishing, replace it with a placeholder and keep your real credentials private.

## Author
Anirudh Paul
