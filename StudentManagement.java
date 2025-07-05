import java.io.*;
import java.util.*;

public class StudentManagement {

    static final String FILE_NAME = "students.txt";

    // Method to add a new student
    public static void addStudent() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Roll No: ");
        String roll = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Course: ");
        String course = sc.nextLine();
        System.out.print("Enter Marks: ");
        String marks = sc.nextLine();

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
        bw.write(roll + "," + name + "," + course + "," + marks);
        bw.newLine();
        bw.close();
        System.out.println("Student record added successfully!\n");
    }

    // Method to display all students
    public static void viewStudents() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        System.out.println("Roll\tName\t\tCourse\tMarks");
        System.out.println("----------------------------------------");
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data.length >= 4)
                System.out.printf("%s\t%s\t%s\t%s\n", data[0], data[1], data[2], data[3]);
        }
        br.close();
    }

    // Search student by roll number
    public static void searchStudent(String rollNo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));
        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(rollNo)) {
                System.out.println("Student Found:");
                System.out.println("Roll: " + data[0]);
                System.out.println("Name: " + data[1]);
                System.out.println("Course: " + data[2]);
                System.out.println("Marks: " + data[3]);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Student with Roll No " + rollNo + " not found.");
        }
        br.close();
    }

    // Method to update a student's data
    public static void updateStudent(String rollNo) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        Scanner sc = new Scanner(System.in);

        String line;
        boolean updated = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (data[0].equals(rollNo)) {
                System.out.print("Enter New Name: ");
                String name = sc.nextLine();
                System.out.print("Enter New Course: ");
                String course = sc.nextLine();
                System.out.print("Enter New Marks: ");
                String marks = sc.nextLine();

                bw.write(rollNo + "," + name + "," + course + "," + marks);
                bw.newLine();
                updated = true;
            } else {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        bw.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (updated)
            System.out.println("Student record updated successfully!");
        else
            System.out.println("Student with Roll No " + rollNo + " not found.");
    }

    // Method to delete a student
    public static void deleteStudent(String rollNo) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(inputFile));
        BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
        String line;
        boolean deleted = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (!data[0].equals(rollNo)) {
                bw.write(line);
                bw.newLine();
            } else {
                deleted = true;
            }
        }

        br.close();
        bw.close();

        inputFile.delete();
        tempFile.renameTo(inputFile);

        if (deleted)
            System.out.println("Student record deleted successfully!");
        else
            System.out.println("Student with Roll No " + rollNo + " not found.");
    }

    // Main menu
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n---- Student Record Management ----");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3:
                    System.out.print("Enter Roll No to Search: ");
                    String rollSearch = sc.nextLine();
                    searchStudent(rollSearch);
                    break;
                case 4:
                    System.out.print("Enter Roll No to Update: ");
                    String rollUpdate = sc.nextLine();
                    updateStudent(rollUpdate);
                    break;
                case 5:
                    System.out.print("Enter Roll No to Delete: ");
                    String rollDelete = sc.nextLine();
                    deleteStudent(rollDelete);
                    break;
                case 6: System.out.println("Exiting..."); break;
                default: System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }
}
