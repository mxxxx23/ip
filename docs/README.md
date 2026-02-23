# Sago - Your Desktop Task Manager

Sago is a **desktop task manager chatbot** built with Java and JavaFX.  
It supports fast keyboard interaction while providing a clean graphical interface.

---

## 📑 Table of Contents

- [Quick Start](#-quick-start)
- [Features](#-features)
    - [Add a Todo](#-add-a-todo)
    - [Add a Deadline](#-add-a-deadline)
    - [Add an Event](#-add-an-event)
    - [List Tasks](#-list-tasks)
    - [Mark a Task as Done](#-mark-a-task-as-done)
    - [Unmark a Task](#-unmark-a-task)
    - [Delete a Task](#-delete-a-task)
    - [Find Tasks](#-find-tasks)
    - [Help](#-help)
- [Command Summary](#-command-summary)
- [Screenshot](#-screenshot)
- [Notes](#-notes)

---

## 🚀 Quick Start

> [!IMPORTANT]
> Ensure you have **Java 17** installed before running the application.

1. Download the latest `.jar` file from the **GitHub Releases** page.
2. Open a terminal in the folder containing the `.jar` file.
3. Run:

```
java -jar sago.jar
```

---

## ✨ Features

---

### 📝 Add a Todo

Adds a simple task without a date.

**Command format**

```
todo DESCRIPTION
```

**Example**

```
todo read book
```

---

### ⏰ Add a Deadline

Adds a task with a due date.

**Command format**

```
deadline DESCRIPTION /by YYYY-MM-DD
```

**Example**

```
deadline finish slides /by 2026-01-31
```

---

### 📅 Add an Event

Adds a task with a start and end date.

**Command format**

```
event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD
```

**Example**

```
event complete essay /from 2026-03-01 /to 2026-03-02
```

---

### 📋 List Tasks

Displays all tasks currently stored.

```
list
```

---

### ✅ Mark a Task as Done

Marks a task as completed.

**Command format**

```
mark INDEX
```

**Example**

```
mark 1
```

---

### 🔄 Unmark a Task

Marks a completed task as not done.

**Command format**

```
unmark INDEX
```

**Example**

```
unmark 1
```

---

### ❌ Delete a Task

Deletes a task by its index.

**Command format**

```
delete INDEX
```

**Example**

```
delete 1
```

---

### 🔍 Find Tasks

Finds tasks that contain a keyword.

**Command format**

```
find KEYWORD
```

**Example**

```
find book
```

Displays all tasks containing the word "book".

---

### ❓ Help

Displays the help message with a summary of commands.

```
help
```

---

## 📌 Command Summary

| Action | Command |
|--------|---------|
| Add Todo | `todo DESCRIPTION` |
| Add Deadline | `deadline DESCRIPTION /by YYYY-MM-DD` |
| Add Event | `event DESCRIPTION /from YYYY-MM-DD /to YYYY-MM-DD` |
| List Tasks | `list` |
| Mark Task | `mark INDEX` |
| Unmark Task | `unmark INDEX` |
| Delete Task | `delete INDEX` |
| Find Tasks | `find KEYWORD` |
| Help | `help` |

---

## 🖼️ Screenshot

![Sago UI](Ui.png)

---

## 📌 Notes

> [!NOTE]
> - Task indices start from **1**.
> - Dates must follow the format `YYYY-MM-DD`.
> - Data is automatically saved after each command.
> - Invalid commands will display an error message.

---