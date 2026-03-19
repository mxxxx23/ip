# Sago

Sago is a desktop task manager chatbot built with Java and JavaFX.
It is designed for fast keyboard-based task management with a simple GUI.

![Sago UI](docs/Ui.png)

## Overview

Sago helps users keep track of tasks such as todos, deadlines, and events.
It supports common task operations such as adding, listing, marking,
unmarking, deleting, and finding tasks.

## Links

- [User Guide](docs/README.md)
- [Source Code](src/main/java)

## Quick Start

1. Ensure you have Java 17 installed.
2. Build the jar:

```bash
./gradlew shadowJar
```

3. Run the app:

```bash
java -jar build/libs/sago.jar
```

## Features

- Add todos, deadlines, and events
- Mark and unmark tasks
- Delete tasks
- Find tasks by keyword
- Use the app through a JavaFX chat-style interface

For full command details and examples, see the [User Guide](docs/README.md).
