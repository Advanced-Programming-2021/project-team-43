package controller;

import java.sql.*;

public class Database {

    private static String address;

    public static void createNewDatabase() {
        address = "jdbc:sqlite:F:/project-team-43/database.db";
        try {
            Connection connection = DriverManager.getConnection(address);
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
        } catch (SQLException ignored) {
        }
    }

    public static void createNewTable() {
        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS UserModel (\n"
                + " username String PRIMARY KEY,\n"
                + " password String NOT NULL,\n"
                + " nickname String NOT NULL,\n"
                + " userScore int NOT NULL,\n"
                + " userCoin int NOT NULL)";

        try{
            Connection connection = DriverManager.getConnection(address);
            Statement statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/SSSIT.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }


    public static void insert() {
        Connection c = null;
        Statement stmt = null;
        try {
            c = DriverManager.getConnection(address);
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO database (username,password,nickname,userScore,userCoin) " +
                    "VALUES ('forooz', '123', 'forooz', 10, 20000);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO database (username,password,nickname,userScore,userCoin) " +
                    "VALUES ('roya', '123', 'roya', 1000, 20000);";
            stmt.executeUpdate(sql);

            stmt.close();
            //  c.commit();
            c.close();
        } catch (Exception e) {
            System.out.println("cant insert");
        }
        System.out.println("Records created successfully");
    }

}