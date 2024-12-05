package StudentGradeTracker;


import java.io.*;
import java.util.ArrayList;

public class FileManager {
    private static final String FILE_NAME = "students.txt";

    public static void saveStudents(ArrayList<Student> students) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Student student : students) {
                writer.write(student.getName() + ":" + student.getGrades().toString().replace("[", "").replace("]", "").replace(" ", ""));
                writer.newLine();
            }
        }
    }

    public static ArrayList<Student> loadStudents() throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                Student student = new Student(parts[0]);
                if (parts.length > 1) {
                    for (String grade : parts[1].split(",")) {
                        student.addGrade(Integer.parseInt(grade));
                    }
                }
                students.add(student);
            }
        }
        return students;
    }
}
