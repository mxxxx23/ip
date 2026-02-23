# Sago Task Manager

Sago is a desktop task manager chatbot optimized for fast keyboard interaction, with a simple JavaFX GUI. It allows users to manage todos, deadlines, and events efficiently.

---

## 🚀 Quick Start

1. Ensure you have **Java 17** installed.
2. Download the latest `.jar` file from the GitHub Releases page.
3. Open a terminal and run:

```
java -jar sago.jar
```

---

## ✨ Features

### 📝 Add a Todo

Adds a simple task without a date.

```
todo read book
```

Expected output:
The task will be added to your task list.

---

### ⏰ Add a Deadline

Adds a task with a due date.

```
deadline finish slides /by 2026-01-31
```

Expected output:
The deadline task will be added with the specified due date.

---

### 📅 Add an Event

Adds a task with a start and end date.

```
event complete essay /from 2026-03-01 /to 2026-03-02
```

Expected output:
The event task will be added with the specified date range.

---

### 📋 List Tasks

Displays all tasks in your list.

```
list
```

Expected output:
All current tasks will be shown with their status and details.

---

### ✅ Mark a Task as Done

Marks a task as completed.

```
mark 1
```

Expected output:
The specified task will be marked as done.

---

### ❌ Delete a Task

Deletes a task by index.

```
delete 1
```

Expected output:
The specified task will be removed from the list.

---

## 🖼️ Screenshot

![Ui](Ui.png)

---

## 📌 Notes

- Task indices start from 1.
- Dates must follow the format `YYYY-MM-DD`.
- The application automatically saves data after each command.