import java.util.*;
import java.io.*;


class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }
    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public String toString() { return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade; }
}


class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();
    private String filename = "students.dat";


    public boolean addStudent(Student student) {
        for (Student s : students) {
            if (s.getRollNumber() == student.getRollNumber()) return false; // Duplicate
        }
        students.add(student);
        return true;
    }


    public boolean removeStudent(int rollNumber) {
        return students.removeIf(s -> s.getRollNumber() == rollNumber);
    }

   
    public Student searchStudent(int rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNumber) return s;
        }
        return null;
    }

   
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            for (Student s : students) {
                System.out.println(s);
            }
        }
    }

  
    public boolean editStudent(int rollNumber, String newName, String newGrade) {
        Student s = searchStudent(rollNumber);
        if (s != null) {
            s.setName(newName);
            s.setGrade(newGrade);
            return true;
        }
        return false;
    }


    @SuppressWarnings("unchecked")
    public void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) { // File might not exist
            students = new ArrayList<>();
        }
    }

  
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }
}

public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();
        sms.loadFromFile();

        while (true) {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Search Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            String input = scan.nextLine();

            switch (input) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter roll number: ");
                    int roll;
                    try { roll = Integer.parseInt(scan.nextLine()); } catch (Exception e) { System.out.println("Invalid roll number!"); break; }
                    System.out.print("Enter grade: ");
                    String grade = scan.nextLine();
                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Name and grade cannot be empty.");
                        break;
                    }
                    Student student = new Student(name, roll, grade);
                    if (sms.addStudent(student))
                        System.out.println("Student added successfully.");
                    else
                        System.out.println("Student with this roll number already exists.");
                    break;

                case "2":
                    System.out.print("Enter roll number to remove: ");
                    try {
                        roll = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid roll number!");
                        break;
                    }
                    if (sms.removeStudent(roll))
                        System.out.println("Student removed successfully.");
                    else
                        System.out.println("Student not found.");
                    break;

                case "3":
                    System.out.print("Enter roll number to edit: ");
                    try {
                        roll = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid roll number!");
                        break;
                    }
                    System.out.print("Enter new name: ");
                    name = scan.nextLine();
                    System.out.print("Enter new grade: ");
                    grade = scan.nextLine();
                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Name and grade cannot be empty.");
                        break;
                    }
                    if (sms.editStudent(roll, name, grade))
                        System.out.println("Student updated successfully.");
                    else
                        System.out.println("Student not found.");
                    break;

                case "4":
                    System.out.print("Enter roll number to search: ");
                    try {
                        roll = Integer.parseInt(scan.nextLine());
                    } catch (Exception e) {
                        System.out.println("Invalid roll number!");
                        break;
                    }
                    Student s = sms.searchStudent(roll);
                    if (s != null)
                        System.out.println(s);
                    else
                        System.out.println("Student not found.");
                    break;

                case "5":
                    sms.displayStudents();
                    break;

                case "6":
                    sms.saveToFile();
                    System.out.println("Exiting. Data saved.");
                    scan.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

