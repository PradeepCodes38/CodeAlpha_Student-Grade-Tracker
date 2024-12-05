package StudentGradeTracker;


import java.util.ArrayList;

public class Student {
    private String name;
    private ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Integer> getGrades() {
        return grades;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double calculateAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public int getHighestGrade() {
        return grades.stream().max(Integer::compare).orElse(0);
    }

    public int getLowestGrade() {
        return grades.stream().min(Integer::compare).orElse(0);
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', grades=" + grades + "}";
    }
}
