
package com.example.hostelmanagement;

import java.util.Scanner;

public class HostelManagementApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n==================================");
            System.out.println(" KIET HOSTEL MANAGEMENT SYSTEM");
            System.out.println("==================================");

            System.out.println("1. Insert Hostel Student Records");
            System.out.println("2. Search Students by Hostel and Fee");
            System.out.println("3. Update Hostel Fee");
            System.out.println("4. Search and Update Room Number");
            System.out.println("5. Total Fee and Delete Student");
            System.out.println("0. Exit");

            System.out.print("\nEnter Your Choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    HostelStudentOperations.insertStudents(sc);
                    break;

                case 2:
                    HostelStudentOperations.searchStudents(sc);
                    break;

                case 3:
                    HostelStudentOperations.updateFee(sc);
                    break;

                case 4:
                    HostelStudentOperations.updateRoom(sc);
                    break;

                case 5:
                    HostelStudentOperations.deleteStudent(sc);
                    break;

                case 0:
                    System.out.println("Thank You! Application Closed.");
                    break;

                default:
                    System.out.println("Invalid Choice! Try Again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
