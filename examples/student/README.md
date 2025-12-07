# Student Example

A student management application demonstrating the JMiniApp framework.

## Overview

This example shows how to create a CRUD (Create, Read, Update, Delete) mini-app using JMiniApp core that manages a list of students. Users can add, modify, delete students, and persist data through JSON import/export via an interactive menu.

## Features

- **Add Student**: Create new student records with name and score
- **Remove Student**: Delete students by ID
- **Modify Student**: Update student name and/or score
- **Export to JSON**: Save all student records to a JSON file
- **Import from JSON**: Load student records from a JSON file
- **Persistent State**: Student list is automatically saved on exit

## Project Structure

```
student/
├── pom.xml
├── README.md
└── src/main/java/com/jminiapp/examples/student/
    ├── StudentApp.java          # Main application class
    ├── StudentState.java        # Student model
    └── StudentJSONAdapter.java  # JSON format adapter
```

## Key Components

### StudentState
A model class representing a student with properties:
- `id`: Unique identifier (auto-generated)
- `name`: Student's name
- `score`: Student's score/grade
- `toString()`: Returns formatted student information

### StudentJSONAdapter
A format adapter that enables JSON import/export for `StudentState`:
- Implements `JSONAdapter<StudentState>` from the framework
- Provides automatic serialization/deserialization for student lists

### StudentApp
The main application class that extends `JMiniApp` and implements:
- `initialize()`: Set up the app and load existing student data
- `run()`: Main loop displaying menu and handling user input
- `shutdown()`: Export student data before exiting
- Uses framework's `context.importData()` and `context.exportData()` for file operations

## Building and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Build the project

From the **project root** (not the examples/student directory):
```bash
mvn clean install
```

This will build both the jminiapp-core module and the student example.

### Run the application

Option 1: Using Maven exec plugin (from the examples/student directory)
```bash
cd examples/student
mvn clean packagee
mvn exec:java
```



## Usage Example

### Adding a Student

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
Student: 1 Name: John Doe Score: 95
```

### Listing Students

Students are displayed automatically at the top of each menu:

```
Student: 1 Name: John Doe Score: 95
Student: 2 Name: Jane Smith Score: 87
1. Add Student
2. Remove Student
3. Change Student
4. Export to JSON
5. Import from JSON
6. Exit

Select a option:
```

### Modifying a Student

```
Select a option: 3
Enter student ID to modify: 1
Current student: Student: 1 Name: John Doe Score: 95
Enter new name (leave empty to skip): John Smith
Enter new score (leave empty to skip): 98
Student modified successfully!
Student: 1 Name: John Smith Score: 98
```

### Deleting a Student

```
Select a option: 2
Enter student ID to delete: 2
Student to delete: Student: 2 Name: Jane Smith Score: 87
Student deleted successfully!
```

### Export to JSON

```
Select a option: 4
Student state exported successfully to: Student.json
```

The exported JSON file will look like:
```json
[
  {
    "id": 1,
    "name": "John Smith",
    "score": 98
  },
  {
    "id": 3,
    "name": "Alice Johnson",
    "score": 92
  }
]
```

### Import from JSON

```
Select a option: 5
Student state imported successfully from Student.json!
```
