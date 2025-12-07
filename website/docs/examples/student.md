---
sidebar_position: 2
---

# Student Example Application

A CRUD (Create, Read, Update, Delete) student management application demonstrating comprehensive state management and data persistence.

**Features:**
- Add, modify, and delete student records
- List all students with ID, name, and score
- State persistence with auto-save on exit
- JSON import/export
- Interactive menu system with input validation

**Source Code:** [examples/student](https://github.com/jminiapp/jminiapp/tree/main/examples/student)

### Key Concepts Demonstrated

- Application lifecycle (initialize, run, shutdown)
- List-based state management
- CRUD operations
- JSON format adapter implementation
- Framework-based import/export
- Input validation and error handling

### Quick Start

```bash
cd examples/student
mvn clean install
mvn exec:java
```

### Code Highlights

**State Model:**
```java
public class StudentState {
    private int id;
    private String name;
    private int score;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    public String toString() {
        return "ID: " + getId() + " Name: " + getName() + " Score: " + getScore();
    }
}
```

**Application:**
```java
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

    private void addNewStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter student score: ");
        String scoreInput = scanner.nextLine().trim();
        int score = Integer.parseInt(scoreInput);

        int newId = students.isEmpty() ? 1 :
            students.get(students.size() - 1).getId() + 1;

        StudentState newStudent = new StudentState();
        newStudent.setId(newId);
        newStudent.setName(name);
        newStudent.setScore(score);

        students.add(newStudent);
        System.out.println("Student added successfully!");
        System.out.println(newStudent.toString());
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

    public static void main(String[] args) {
        JMiniAppRunner.forApp(StudentApp.class).run(args);
    }
}
```

**JSON Adapter:**
```java
public class StudentJSONAdapter implements JSONAdapter<StudentState> {
    @Override
    public Class<StudentState> getstateClass() {
        return StudentState.class;
    }
}
```

**Bootstrap:**
```java
public class StudentAppRunner {
    public static void main(String[] args) {
        JMiniAppRunner
            .forApp(StudentApp.class)
            .withState(StudentState.class)
            .withAdapters(new StudentJSONAdapter())
            //.withResourcesPath("test-data/")  // Custom import/export path
            .named("Student")
            .run(args);
    }
}

```

### Usage Example

#### Adding Students
```
=== Student App ===
1. Add Student
2. Remove Student
3. Change Student
4. Export to JSON
5. Import from JSON
6. Exit

Select a option: 1
Enter student name: John Doe
Enter student score: 95
Student added successfully!
ID: 1 Name: John Doe Score: 95
```

#### Listing Students
Students are displayed automatically at the menu:
```
ID: 1 Name: John Doe Score: 95
ID: 2 Name: Jane Smith Score: 87
1. Add Student
2. Remove Student
3. Change Student
4. Export to JSON
5. Import from JSON
6. Exit
```

#### Modifying Students
```
Select a option: 3
Enter student ID to modify: 1
Current student: ID: 1 Name: John Doe Score: 95
Enter new name (leave empty to skip): John Smith
Enter new score (leave empty to skip): 98
Student modified successfully!
ID: 1 Name: John Smith Score: 98
```

#### JSON Export Format
```json
[
  {
    "id": 1,
    "name": "John Smith",
    "score": 98
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "score": 87
  }
]
```

This example demonstrates how to build a complete CRUD application using JMiniApp. The framework handles state persistence and file I/O, letting you focus on implementing business logic and user interactions.
