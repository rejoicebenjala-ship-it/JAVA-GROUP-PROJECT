# Time Management System

## Project Overview

The Time Management System is a Java desktop application designed to help users organise tasks, manage deadlines, and track task completion.

The application uses Java Swing for its graphical user interface and MySQL for persistent data storage. It also includes a background reminder thread that checks for unfinished tasks that are due or overdue.

## Features

* **Add Tasks:** Create tasks with a title, description, due date, and priority.
* **View Tasks:** Display saved tasks in the application.
* **Complete Tasks:** Mark selected tasks as completed.
* **Database Persistence:** Save and retrieve tasks using MySQL.
* **Task Reminders:** Check for due and overdue tasks in the background.
* **Input Validation:** Validate task titles and date formats.
* **Exception Handling:** Handle database and input errors.
* **Object-Oriented Design:** Use classes, interfaces, inheritance, and method overriding.

## Technologies Used

* Java
* Java Swing
* JDBC (Java Database Connectivity)
* MySQL
* MySQL Connector/J
* Visual Studio Code

## Project Structure

```text
JAVA PROJECTS/
├── src/
│   ├── dao/
│   │   └── DatabaseConnection.java
│   ├── gui/
│   │   └── TaskGUI.java
│   ├── interfaces/
│   │   └── TaskOperations.java
│   ├── model/
│   │   └── Task.java
│   ├── service/
│   │   └── TaskService.java
│   ├── threads/
│   │   └── TaskReminderThread.java
│   ├── App.java
│   └── DatabaseTest.java
├── lib/
│   └── mysql-connector-j-26.7.0.jar
├── .gitignore
└── README.md
```

## Requirements

Before running the application, ensure you have:

1. A compatible Java Development Kit (JDK).
2. MySQL Server installed and running.
3. MySQL Connector/J available in the `lib` directory.
4. Visual Studio Code or another Java development environment.
5. A database password configured in your Windows user environment variables.

## Database Setup

Open MySQL Workbench and connect to your local MySQL Server.

Execute the following SQL statements to create the database and table:

```sql
CREATE DATABASE IF NOT EXISTS time_management_db;

USE time_management_db;

CREATE TABLE IF NOT EXISTS tasks (
    id INT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    due_date DATE NOT NULL,
    priority VARCHAR(20) NOT NULL,
    completed BOOLEAN NOT NULL DEFAULT FALSE
);
```

The application connects to the database using JDBC.

## Database Configuration

The application reads the database password from the `DB_PASSWORD` environment variable.

On Windows:

1. Open the Windows environment variable settings.
2. Create a user environment variable named `DB_PASSWORD`.
3. Set its value to your local MySQL password.
4. Restart your development environment so it can access the variable.

Never commit database passwords or other secrets to a public repository.

Ensure that `DatabaseConnection.java` uses the environment variable rather than a password written directly in the source code.

## How to Compile and Run

Open a PowerShell terminal in the project directory.

### Step 1: Compile

Run:

```powershell
javac -cp "lib\mysql-connector-j-26.7.0.jar" -d out src\App.java src\model\Task.java src\interfaces\TaskOperations.java src\dao\DatabaseConnection.java src\service\TaskService.java src\gui\TaskGUI.java src\threads\TaskReminderThread.java
```

### Step 2: Run

Run:

```powershell
java -cp "out;lib\mysql-connector-j-26.7.0.jar" App
```

The Time Management System window should open.

## Testing

The following features have been tested:

* Opening the desktop application.
* Loading previously saved tasks.
* Adding tasks to the database.
* Retrieving tasks after restarting the application.
* Marking tasks as completed.
* Displaying reminders for unfinished tasks that are due or overdue.
* Compiling the application without errors.

## Future Improvements

Potential future improvements include:

* Editing and deleting tasks.
* Filtering tasks by priority or completion status.
* Sorting tasks by deadline.
* Improving the visual design.
* Adding automated unit tests.

## Author

Developed as a Java programming project.
