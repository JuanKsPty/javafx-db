package com.juank.javafx.parcial3.javafxdbparcial3;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestDb {
    public static void main(String[] args) {
        String URL = "jdbc:sqlserver://localhost:1433;databaseName=BD_NUEVAJAVA;user=sa;password=Password123#;encrypt=false;trustServerCertificate=true";

        try {
            // Test connection
            Connection connection = DriverManager.getConnection(URL);
            System.out.println("✅ Connection successful!");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) as total FROM users");

            if (resultSet.next()) {
                int count = resultSet.getInt("total");
                System.out.println("Total users in database: " + count);
            }


            ResultSet users = statement.executeQuery("SELECT TOP 3 id, name, email FROM users");
            System.out.println("\nSample users:");
            while (users.next()) {
                System.out.println("- " + users.getInt("id") +
                        " | " + users.getString("name") +
                        " | " + users.getString("email"));
            }

            resultSet.close();
            users.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}