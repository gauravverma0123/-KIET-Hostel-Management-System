
package com.example.hostelmanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Scanner;

public class HostelStudentOperations {

    // (a) INSERT THREE STUDENT RECORDS

    public static void insertStudents(Scanner sc) {

        String sql = "INSERT INTO HostelStudent " +
                "(student_id, student_name, roll_number, " +
                "hostel_name, room_number, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 1; i <= 3; i++) {

                System.out.println("\nEnter Student " + i + " Details");

                System.out.print("Enter Student ID: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter Student Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Roll Number: ");
                String roll = sc.nextLine();

                System.out.print("Enter Hostel Name: ");
                String hostel = sc.nextLine();

                System.out.print("Enter Room Number: ");
                String room = sc.nextLine();

                System.out.print("Enter Hostel Fee: ");
                double fee = sc.nextDouble();
                sc.nextLine();

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, roll);
                ps.setString(4, hostel);
                ps.setString(5, room);
                ps.setDouble(6, fee);

                try {
                    ps.executeUpdate();
                    System.out.println("Student Inserted Successfully!");

                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Duplicate Student ID!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Insert Error: " + e.getMessage());
        }
    }


    // (b) CONDITIONAL RETRIEVAL AND SORTING

    public static void searchStudents(Scanner sc) {

        String sql = "SELECT * FROM HostelStudent " +
                "WHERE hostel_name = ? AND fee > 50000 " +
                "ORDER BY fee DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Hostel Name: ");
            String hostel = sc.nextLine();

            ps.setString(1, hostel);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {

                    found = true;

                    System.out.println("\nStudent ID: " +
                            rs.getInt("student_id"));

                    System.out.println("Student Name: " +
                            rs.getString("student_name"));

                    System.out.println("Roll Number: " +
                            rs.getString("roll_number"));

                    System.out.println("Hostel Name: " +
                            rs.getString("hostel_name"));

                    System.out.println("Room Number: " +
                            rs.getString("room_number"));

                    System.out.println("Hostel Fee: Rs. " +
                            rs.getDouble("fee"));
                }

                if (!found) {
                    System.out.println("No Matching Students Found!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Search Error: " + e.getMessage());
        }
    }


    // (c) UPDATE HOSTEL FEE

    public static void updateFee(Scanner sc) {

        String sql = "UPDATE HostelStudent SET fee = ? " +
                "WHERE student_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();

            System.out.print("Enter New Hostel Fee: ");
            double fee = sc.nextDouble();
            sc.nextLine();

            ps.setDouble(1, fee);
            ps.setInt(2, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Hostel Fee Updated Successfully!");
            } else {
                System.out.println("Student Record Does Not Exist!");
            }

        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
    }


    // (d) SEARCH AND UPDATE ROOM NUMBER

    public static void updateRoom(Scanner sc) {

        try (Connection con = DBConnection.getConnection()) {

            System.out.print("Enter Student ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            String search = "SELECT * FROM HostelStudent " +
                    "WHERE student_id = ?";

            try (PreparedStatement ps = con.prepareStatement(search)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {

                    if (rs.next()) {

                        System.out.println("\nStudent Found!");

                        System.out.println("Student Name: " +
                                rs.getString("student_name"));

                        System.out.println("Roll Number: " +
                                rs.getString("roll_number"));

                        System.out.println("Hostel Name: " +
                                rs.getString("hostel_name"));

                        System.out.println("Old Room Number: " +
                                rs.getString("room_number"));

                        System.out.print("Enter New Room Number: ");
                        String room = sc.nextLine();

                        String update = "UPDATE HostelStudent " +
                                "SET room_number = ? WHERE student_id = ?";

                        try (PreparedStatement ps2 =
                                     con.prepareStatement(update)) {

                            ps2.setString(1, room);
                            ps2.setInt(2, id);

                            ps2.executeUpdate();

                            System.out.println("\nRoom Updated Successfully!");

                            // Display updated details

                            String display = "SELECT * FROM HostelStudent " +
                                    "WHERE student_id = ?";

                            try (PreparedStatement ps3 =
                                         con.prepareStatement(display)) {

                                ps3.setInt(1, id);

                                try (ResultSet rs2 = ps3.executeQuery()) {

                                    if (rs2.next()) {

                                        System.out.println("\nUpdated Student Details:");

                                        System.out.println("Student ID: " +
                                                rs2.getInt("student_id"));

                                        System.out.println("Student Name: " +
                                                rs2.getString("student_name"));

                                        System.out.println("Roll Number: " +
                                                rs2.getString("roll_number"));

                                        System.out.println("Hostel Name: " +
                                                rs2.getString("hostel_name"));

                                        System.out.println("New Room Number: " +
                                                rs2.getString("room_number"));

                                        System.out.println("Hostel Fee: " +
                                                rs2.getDouble("fee"));
                                    }
                                }
                            }
                        }

                    } else {
                        System.out.println("Student Record Not Found!");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Room Update Error: " + e.getMessage());
        }
    }


    // (e) AGGREGATE QUERY AND CONDITIONAL DELETION

    public static void deleteStudent(Scanner sc) {

        try (Connection con = DBConnection.getConnection()) {

            // Calculate total hostel fee

            String sumQuery = "SELECT SUM(fee) AS total_fee " +
                    "FROM HostelStudent";

            try (PreparedStatement ps = con.prepareStatement(sumQuery);
                 ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nTotal Hostel Fee Collected: Rs. " +
                            rs.getDouble("total_fee"));
                }
            }

            // Delete student only if fee > 0

            System.out.print("\nEnter Student ID to Delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String delete = "DELETE FROM HostelStudent " +
                    "WHERE student_id = ? AND fee > 0";

            try (PreparedStatement ps = con.prepareStatement(delete)) {

                ps.setInt(1, id);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("Student Deleted Successfully!");
                } else {
                    System.out.println(
                            "Record Not Found or Fee is Zero!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
        }
    }

}
