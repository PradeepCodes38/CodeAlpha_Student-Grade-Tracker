package StudentGradeTracker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GradeTracker {
    private static ArrayList<Student> students = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            students = FileManager.loadStudents();
        } catch (IOException e) {
            System.out.println("No previous data found. Starting fresh!");
        }

        while (true) {
            System.out.println("\n===== Student Grade Tracker =====");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Grades");
            System.out.println("3. View Grades");
            System.out.println("4. View Class Statistics");
            System.out.println("5. Save & Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> enterGrades();
                case 3 -> viewGrades();
                case 4 -> viewStatistics();
                case 5 -> {
                    saveDataAndExit();
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(name));
        System.out.println("Student added successfully!");
    }

    private static void enterGrades() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        Student student = findStudentByName(name);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        System.out.print("Enter grades (comma-separated): ");
        String[] grades = scanner.nextLine().split(",");
        for (String grade : grades) {
            student.addGrade(Integer.parseInt(grade.trim()));
        }
        System.out.println("Grades added successfully!");
    }

    private static void viewGrades() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private static void viewStatistics() {
        if (students.isEmpty()) {
            System.out.println("No students to calculate statistics.");
            return;
        }
        double classAverage = students.stream()
                .mapToDouble(Student::calculateAverage)
                .average()
                .orElse(0);

        int highest = students.stream()
                .flatMapToInt(student -> student.getGrades().stream().mapToInt(Integer::intValue))
                .max().orElse(0);

        int lowest = students.stream()
                .flatMapToInt(student -> student.getGrades().stream().mapToInt(Integer::intValue))
                .min().orElse(0);

        System.out.printf("Class Average: %.2f\n", classAverage);
        System.out.println("Highest Grade: " + highest);
        System.out.println("Lowest Grade: " + lowest);
    }

    private static void saveDataAndExit() {
        try {
            FileManager.saveStudents(students);
            System.out.println("Data saved successfully! Exiting...");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static Student findStudentByName(String name) {
        return students.stream().filter(student -> student.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
