# Instructions

## To ensure that the connection to database will work:

1.  Check if MySQL in XAMPP is running. Verify that it has started running.
2.  Check the port that MySQL is using. 
-   If the port is 3306 then you do not need to change anything. 
-   If the port is not 3306 then copy that port number.
-   Go to Database.java File/Class inside the main package that is inside the "src" folder.
-   Replace the value of `defaultPort` property to the port that MySQL is using in your device.
3.  Create a database.
-   Go to Database.java File/Class inside the main package that is inside the "src" folder.
-   Replace the value of `defaultDBname` property to the name of the database that you have just created.
-   Replace the value of `defaultUsername` property to the MySQL username that your device is using.
-   Replace the value of `defaultPassword` property to the MySQL password that your device is using.

### NOTE: Only the values of the specified properties inside the Database.java File/Class is needed to be replaced. You do not need to replace any other variable value in any other class or files.

## To initially login:
-   Username: admin
-   Password: admin123

### NOTE: The default admin account is automatically created. Hence, you just need to type the username and password that is provided above.