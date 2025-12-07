package com.jminiapp.examples.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.jminiapp.core.api.JMiniApp;
import com.jminiapp.core.api.JMiniAppConfig;
import com.jminiapp.core.engine.JMiniAppRunner;

public class StudentApp extends JMiniApp {
    private Scanner scanner;
    private List<StudentState> students;
    private boolean running;
    

    public StudentApp(JMiniAppConfig config) {
        super(config);
    }
    
    @Override
    protected void initialize() {
        System.out.println("\n=== Student App ===");

        scanner = new Scanner(System.in);
        running = true;

        importFromFile();
    }
    
    @Override
    protected void run() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
    }
    
    @Override
    protected void shutdown() {
        exportToFile();
        scanner.close();
        running = false;
        System.out.println("Goodbye!");
    }
    
    public static void main(String[] args) {
        JMiniAppRunner.forApp(StudentApp.class).run(args);
    }


    private void displayMenu() {
        if(students.size()==0){
            System.out.println("There is no students");
        }
        for(int i=0;i<students.size();i++){
            System.out.println(students.get(i));
        }
        System.out.println("1. Add Student");
        System.out.println("2. Remove Student");
        System.out.println("3. Change Student");
        System.out.println("4. Export to JSON");
        System.out.println("5. Import from JSON");
        System.out.println("6. Exit");
        System.out.print("\nSelect a option: ");
    }

    private void handleUserInput() {
        try {
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    addNewStudent();
                    break;

                case "2":
                    deleteStudent();
                    break;

                case "3":
                    modifyStudent();
                    break;

                case "4":
                    exportToFile();
                    break;

                case "5":
                    importFromFile();
                    break;

                case "6":
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option. Please choose 1-6.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void exportToFile() {
        try {
            context.setData(students);
            context.exportData("json");
            System.out.println("Student state exported successfully to: Student.json");
        } catch (IOException e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    private void importFromFile() {
        try {
            context.importData("json");
            List<StudentState> data = context.getData();
            if (data != null && !data.isEmpty()) {
                students = data;
                System.out.println("Student state imported successfully from Student.json!");
            } else {
                students = new ArrayList<StudentState>();
            }
        } catch (IOException e) {
            students = new ArrayList<StudentState>();
        }
    }

    private void addNewStudent(){
        try {
            System.out.print("Enter student name: ");
            String name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
                return;
            }

            System.out.print("Enter student score: ");
            String scoreInput = scanner.nextLine().trim();
            int score = Integer.parseInt(scoreInput);

            int newId = students.isEmpty() ? 1 : students.get(students.size() - 1).getId() + 1;

            StudentState newStudent = new StudentState();
            newStudent.setId(newId);
            newStudent.setName(name);
            newStudent.setScore(score);

            students.add(newStudent);
            System.out.println("Student added successfully!");
            System.out.println(newStudent.toString());
        } catch (NumberFormatException e) {
            System.out.println("Error: Score must be a valid number.");
        }
    }

    private void deleteStudent() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students registered.");
                return;
            }

            System.out.print("Enter student ID to delete: ");
            String idInput = scanner.nextLine().trim();
            int id = Integer.parseInt(idInput);

            StudentState studentToDelete = null;
            for (StudentState student : students) {
                if (student.getId() == id) {
                    studentToDelete = student;
                    break;
                }
            }

            if (studentToDelete == null) {
                System.out.println("Student not found with ID: " + id);
                return;
            }

            System.out.println("Student to delete: " + studentToDelete.toString());
            students.remove(studentToDelete);
            System.out.println("Student deleted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a valid number.");
        }
    }

    private void modifyStudent() {
        try {
            if (students.isEmpty()) {
                System.out.println("No students registered.");
                return;
            }

            System.out.print("Enter student ID to modify: ");
            String idInput = scanner.nextLine().trim();
            int id = Integer.parseInt(idInput);

            StudentState studentToModify = null;
            for (StudentState student : students) {
                if (student.getId() == id) {
                    studentToModify = student;
                    break;
                }
            }

            if (studentToModify == null) {
                System.out.println("Student not found with ID: " + id);
                return;
            }

            System.out.println("Current student: " + studentToModify.toString());

            System.out.print("Enter new name (leave empty to skip): ");
            String newName = scanner.nextLine().trim();
            if (!newName.isEmpty()) {
                studentToModify.setName(newName);
            }

            System.out.print("Enter new score (leave empty to skip): ");
            String newScoreInput = scanner.nextLine().trim();
            if (!newScoreInput.isEmpty()) {
                int newScore = Integer.parseInt(newScoreInput);
                studentToModify.setScore(newScore);
            }

            System.out.println("Student modified successfully!");
            System.out.println(studentToModify.toString());
        } catch (NumberFormatException e) {
            System.out.println("Error: ID and score must be valid numbers.");
        }
    }
}