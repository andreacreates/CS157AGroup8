
# CS157A Group8
### Database Management Class SJSU Fall 2018

### How to run application

1. All source files are in `project/src` folder. Import into an IDE of your choosing or compile them through CLI.
2. Install JavaFX
3. Make sure the jar file `project/mysql-connector-java-8.0.13.jar` is referenced in your IDE or included in the classpath if using CLI.
4. Simply run the main method of the class `GUI.java` and the application will run.

### Configuration
* Our database connection is connecting to a cloud database. You can configure the database connection in the `project/src/DataBaseConnection.java` file.
* Enter the Database information and credentials within this class:
```
// JDBC driver name and database URL
private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; 
private static final String DB_URL = "DATABASE ADDRESS";
// Database credentials
private static final String USER = "root";
private static final String PASS = "password";
``` 
* Our hosted database server credentials are already configured in the Class.
* If you want to run our application locally connecting to a local database, please run our SQL script to create the database locally: `setup.sql`
