
package com.example.hostelmanagement;

import java.sql.Connection;

public class DBConnectionTest {

    public static void main(String[] args) {

        try {
            Connection con = DBConnection.getConnection();

            System.out.println("MySQL Connected Successfully!");

            con.close();

        } catch (Exception e) {

            System.out.println("Connection Failed!");
            System.out.println(e.getMessage());

        }
    }
}
