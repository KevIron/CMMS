package com.keviron.cmms.Database;


import java.sql.*;

public class H2DatabaseConnector {

    static String createCustomersTable = "CREATE TABLE IF NOT EXISTS Customers" +
                                         "(id INTEGER not NULL AUTO_INCREMENT," +
                                         "clientName VARCHAR(255)," +
                                         "clientSurname VARCHAR(255)," +
                                         "phoneNumber VARCHAR(255)," +
                                         "PRIMARY KEY (id))";

    static String createRepairsTable = "CREATE TABLE IF NOT EXISTS Repairs" +
                                       "(id INTEGER not NULL AUTO_INCREMENT," +
                                       "clientID INTEGER," +
                                       "carBrand VARCHAR(255)," +
                                       "carName VARCHAR(255)," +
                                       "WIN VARCHAR(255)," +
                                       "date VARCHAR(255)," +
                                       "repairActivities VARCHAR(1500)," +
                                       "PRIMARY KEY (id))";

    public static Connection getConnection() throws SQLException {
        String jdbcURL = "jdbc:h2:~/CMMS/CMMS_Data";
        String username = "Application";
        String password = "HJKFDR146";

        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connection started!");

        Statement stmt = connection.createStatement();
        stmt.execute(createCustomersTable);
        stmt.execute(createRepairsTable);

        return connection;
    }
}
