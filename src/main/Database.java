package main;

import java.sql.*;
import javax.swing.*;

public class Database {
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    String defaultAccountUsername = "admin";
    String defaultAccountPassword = "admin123";

    static int defaultPort = 3306;
    static String defaultDBname = "projectko";
    static String defaultUsername = "root";
    static String defaultPassword = "complexpassword";

    public Database(int port, String dbname, String username, String password) {
        try {
            String url = String.format("jdbc:mysql://localhost:%d/%s", port, dbname);
            connection = DriverManager.getConnection(url, username, password);
            // System.out.println("Connection Established from Database class");
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - constructor method");
            System.out.println("Error in Database class - constructor method" + e.getMessage());
        }
    }

    public static int getPort() {
        return defaultPort;
    }

    public static String getDBname() {
        return defaultDBname;
    }

    public static String getUsername() {
        return defaultUsername;
    }

    public static String getPassword() {
        return defaultPassword;
    }

    public int count(String table, String[] wheres, String[] values) {
        String query = String.format("SELECT count(*) AS Total FROM %s ", table);
        int total = 0;
        query += this.getMultipleWhere(wheres, values, "count");
        // System.out.println(query);
        this.createStatement();

        try {
            resultSet = statement.executeQuery(query);
            resultSet.next();
            total = Integer.parseInt(resultSet.getString("Total"));
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - count method");
        }
        return total;
    }

    public String select(String table, String[] fields, String[] wheres, String[] values, String alias) {
        String query = "SELECT ";
        int fieldsLength = fields.length;

        if (fieldsLength > 0) {
            for (int i = 0; i < fieldsLength; i++) {
                query += i == fieldsLength - 1 ? fields[i] + " " : fields[i] + ", ";
            }
        } else {
            query += "* ";
        }

        if (alias.length() > 0) {
            query = String.format(query + "AS %s ", alias);
        }

        query = String.format(query + "FROM %s " + this.getMultipleWhere(wheres, values, "select"), table);
        try {
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - select method");
        }
        // System.out.println(query);
        return query;
    }

    public boolean hasNext() {
        boolean result = false;
        try {
            if (resultSet.next()) {
                result = true;
            }
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - hasNext method");
        }
        return result;
    }

    public String getStringFromResult(String key, boolean next) {
        String resultString = "";
        try {
            if (next) {
                resultSet.next();
            }

            resultString = resultSet.getString(key);
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - getStringFromResult method");
        }

        return resultString;
    }

    public String getStringFromResultInt(int key, boolean next) {
        String resultString = "";
        try {
            if (next) {
                resultSet.next();
            }

            resultString = resultSet.getString(key);
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - getStringFromResultInt method");
        }

        return resultString;
    }

    public void createStatement() {
        try {
            this.statement = this.connection.createStatement();
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - createStatement method");
        }
    }

    public void closeDatabase() {
        try {
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - closeDatabase method");
        }
    }

    public void displayResultSet() {
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1)
                        System.out.print(" || ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - displayResultSet method");
        }
    }

    public void setupAccountsTable() {
        this.createAccountsTable();
        this.insertDefaultAccount();
    }

    private String getMultipleWhere(String[] wheres, String[] values, String methodName) {
        int wheresLength = wheres.length;
        int valuesLength = values.length;

        if (wheresLength != valuesLength) {
            String error = String.format(
                    "Error in Database class - %s method: count of where is not equal to count of values", methodName);
            System.err.println(error);
            System.exit(0);
        }

        String query = "";
        if (wheresLength > 0) {
            query += "WHERE ";
            for (int i = 0; i < wheresLength; i++) {
                if (i > 0) {
                    query = String.format(query + " AND %s = '%s'", wheres[i], values[i]);
                } else {
                    query = String.format(query + "%s = '%s'", wheres[i], values[i]);
                }
            }
        }

        // System.out.println(query);
        return query;
    }

    private void createAccountsTable() {
        String query = "CREATE TABLE IF NOT EXISTS `accounts` (`id` int(11) NOT NULL AUTO_INCREMENT, `firstname` varchar(50) NOT NULL, `lastname` varchar(50) NOT NULL, `username` varchar(100) NOT NULL, `password` varchar(200) NOT NULL, PRIMARY KEY (`id`))";
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            this.showError(e.getMessage(), "Error in Database class - createAccountsTable method");
        }
    }

    private void insertDefaultAccount() {
        String[] countWheres = {};
        String[] countValues = {};
        if (this.count("accounts", countWheres, countValues) < 1) {
            String query = String.format(
                    "INSERT INTO `accounts` (`firstname`, `lastname`, `username`, `password`) VALUES ('john', 'wick', '%s', '%s')",
                    this.defaultAccountUsername, Hash.toMD5(this.defaultAccountPassword));
            try {
                statement.executeUpdate(query);
                System.out.println("Successfully Added a Default Account");
            } catch (SQLException e) {
                this.showError(e.getMessage(), "Error in Database class - createAccountsTable method");
            }
        }
    }

    private void showError(String message, String label) {
        JOptionPane.showMessageDialog(null, message, label, JOptionPane.ERROR_MESSAGE);
    }
}
