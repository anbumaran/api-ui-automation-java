
package com.asapp.coding;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnect {

    @Test
    public void testDB() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/postgres",
                            "postgres", "sony12345");
            System.out.println(
                    "Connection Established successfully");
            Statement statement = connection.createStatement();
            String query = "select * from student";
            ResultSet resultSet
                    = statement.executeQuery(query); // Execute query
            while (resultSet.next()) {
                String name
                        = resultSet.getString("stdname"); // Retrieve name from db
                System.out.println(name); // Print result on console
            }

            query = "update student set stdname = 'Anitha' where stdid = 15";
            //statement.executeUpdate(query);
            connection.prepareStatement(query).execute();


            resultSet.close();
            statement.close();
            connection.close();
            System.out.println("Connection Closed....");

            System.out.println("Database closed successfully");

        } catch (SQLException | ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }

    }

}