package org.ordis;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.*;

class TestSQLite {

    private static final String DB_PATH = "jdbc:sqlite:test.db";

    @BeforeAll
    static void init() throws ClassNotFoundException {
        System.out.println("Loaded " + Class.forName("com.mysql.cj.jdbc.Driver"));
    }

    @Test
    void connect() {
        assertDoesNotThrow(() -> {
            try (Connection conn = DriverManager.getConnection(DB_PATH)) {
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new database has been created.");
                }
            }
        });
    }

    @Test
    void create() {
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	capacity real\n"
                + ");";
        assertDoesNotThrow(()->{
            try (Connection conn = DriverManager.getConnection(DB_PATH);
                 Statement stmt = conn.createStatement()) {
                // create a new table
                stmt.execute(sql);
            }
        });
    }

    @Test
    void select() {
        String sql = "SELECT id, name, capacity FROM warehouses";
        assertDoesNotThrow(() -> {
            try (Connection conn = DriverManager.getConnection(DB_PATH);
                 Statement stmt  = conn.createStatement();
                 ResultSet rs    = stmt.executeQuery(sql)){

                // loop through the result set
                while (rs.next()) {
                    System.out.println(rs.getInt("id") +  "\t" +
                            rs.getString("name") + "\t" +
                            rs.getDouble("capacity"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Test
    void insert() {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        assertDoesNotThrow(() -> {
            try (Connection conn = DriverManager.getConnection(DB_PATH);
                 PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, "Value");
                statement.setDouble(2, 320);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
