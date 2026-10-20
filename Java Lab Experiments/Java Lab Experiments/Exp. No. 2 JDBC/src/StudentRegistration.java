import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class StudentRegistration {
    private static final Scanner input = new Scanner(System.in);
    private static final StudentDAO dao = new StudentDAO();

    public static void main(String[] args) {
        System.out.println("=== JDBC Student Registration System ===");

        try {
            DatabaseConnection.getConnection().close();
            System.out.println("Database connection successful.");
        } catch (SQLException e) {
            System.out.println("Could not connect to MySQL.");
            System.out.println("Check MySQL is running and update db.properties.");
            System.out.println("Details: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Update student");
            System.out.println("5. Delete student");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();
            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> viewStudents();
                    case "3" -> searchStudent();
                    case "4" -> updateStudent();
                    case "5" -> deleteStudent();
                    case "0" -> {
                        System.out.println("Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (SQLException e) {
                System.out.println("Database operation failed: " + e.getMessage());
            }
        }
    }

    private static void addStudent() throws SQLException {
        System.out.print("Name: ");
        String name = input.nextLine().trim();
        System.out.print("Email: ");
        String email = input.nextLine().trim();
        System.out.print("Course: ");
        String course = input.nextLine().trim();
        System.out.print("Age: ");
        int age = readAge();

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            System.out.println("Name, email, and course cannot be empty.");
            return;
        }
        Student student = new Student(name, email, course, age);
        System.out.println(dao.addStudent(student)
                ? "Student added successfully." : "Student could not be added.");
    }

    private static void viewStudents() throws SQLException {
        List<Student> students = dao.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.printf("%-5s %-22s %-30s %-20s %-5s%n",
                "ID", "NAME", "EMAIL", "COURSE", "AGE");
        System.out.println("-".repeat(87));
        for (Student s : students) {
            System.out.printf("%-5d %-22s %-30s %-20s %-5d%n",
                    s.getId(), shorten(s.getName(), 21), shorten(s.getEmail(), 29),
                    shorten(s.getCourse(), 19), s.getAge());
        }
    }

    private static void searchStudent() throws SQLException {
        System.out.print("Student ID: ");
        int id = readPositiveInt();
        Student s = dao.getStudentById(id);
        System.out.println(s == null ? "Student not found." : s);
    }

    private static void updateStudent() throws SQLException {
        System.out.print("Student ID to update: ");
        int id = readPositiveInt();
        Student existing = dao.getStudentById(id);
        if (existing == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Enter the new details.");
        System.out.print("Name: ");
        String name = input.nextLine().trim();
        System.out.print("Email: ");
        String email = input.nextLine().trim();
        System.out.print("Course: ");
        String course = input.nextLine().trim();
        System.out.print("Age: ");
        int age = readAge();

        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
            System.out.println("Name, email, and course cannot be empty.");
            return;
        }
        Student updated = new Student(id, name, email, course, age);
        System.out.println(dao.updateStudent(updated)
                ? "Student updated successfully." : "No changes made.");
    }

    private static void deleteStudent() throws SQLException {
        System.out.print("Student ID to delete: ");
        int id = readPositiveInt();
        System.out.println(dao.deleteStudent(id)
                ? "Student deleted successfully." : "Student not found.");
    }

    private static int readAge() {
        while (true) {
            try {
                int age = Integer.parseInt(input.nextLine().trim());
                if (age >= 1 && age <= 120) return age;
            } catch (NumberFormatException ignored) { }
            System.out.print("Enter a valid age (1-120): ");
        }
    }

    private static int readPositiveInt() {
        while (true) {
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (value > 0) return value;
            } catch (NumberFormatException ignored) { }
            System.out.print("Enter a positive number: ");
        }
    }

    private static String shorten(String value, int max) {
        return value.length() <= max ? value : value.substring(0, max - 1) + "…";
    }
}
