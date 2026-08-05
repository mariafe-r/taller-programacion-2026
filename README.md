# taller-programacion-2026

## 📚 Programming Workshop 2026 - Object-Oriented Programming Project

### Project Overview

This repository contains the complete source code and documentation for the Programming Workshop 2026 course at Universidad Manuela Beltrán (UMB). The project demonstrates the practical application of Object-Oriented Programming principles through the development of a comprehensive Library Management System using Java and modern development tools.

### Project Purpose and Objectives

The primary goal of this academic project is to bridge the gap between theoretical OOP concepts and their real-world implementation. Students will develop practical skills in software design, code organization, and the use of professional development tools including GitHub Copilot as an AI pair programmer. The project follows industry best practices such as Conventional Commits for version control, Maven Standard Directory Layout for consistent project structure, and comprehensive unit testing with JUnit 5.

### Complete Project Structure

The project follows the Maven Standard Directory Layout to ensure consistency, maintainability, and easy navigation for all developers. 


### Technology Stack

- **Programming Language:** Java 17 (LTS) for stability and modern features
- **Build Tool:** Gradle with Java and Application plugins for dependency management
- **Testing Framework:** JUnit 5 (Jupiter) with AssertJ for fluent assertions
- **Development Environment:** Visual Studio Code with essential extensions
- **AI-Powered Development:** GitHub Copilot for code generation and reviews
- **Version Control:** Git with GitHub (Conventional Commits)
- **Logging:** SLF4J with Logback for efficient logging

### Object-Oriented Programming Principles Demonstrated

| Pilar | Implementación |
|-------|----------------|
| **Encapsulation** | All domain classes use private fields with public getters/setters. Validations in setters ensure data integrity. |
| **Inheritance** | Author and Member extend Person. All exceptions extend AppException. |
| **Polymorphism** | Overridden toString() methods. Book implements Loanable interface. |
| **Abstraction** | Person is abstract. Loanable is an interface. AppException is abstract. |
| **Composition** | Book has Author. Loan has Book and Member. Library has collections of Books, Members, and Loans. |
| **Delegation** | Library delegates operations to services and repositories. |

### SOLID Principles Applied

| Principle | Implementation |
|-----------|----------------|
| **S - SRP** | Separate classes: LibraryService, ReportService, EmailService, AuditService |
| **O - OCP** | Repository pattern allows new repository types without modifying services |
| **L - LSP** | Exception hierarchy maintains consistent behavior |
| **I - ISP** | Repository interface has focused, specific methods |
| **D - DIP** | Services depend on Repository interfaces, not concrete implementations |

### User Interfaces

The domain and service layers (`domain`, `exception`, `service`) are shared by three
presentation layers, none of which contain business logic of their own:

| Layer | Class | Description |
|-------|-------|--------------|
| Fixed console demo | `com.umb.taller.Main` | Scripted end-to-end walkthrough (loan + return) used for the lab evidence |
| **Interactive console menu** | `com.umb.taller.console.ConsoleMenu` | Text menu (list/add books, register members, loan/return, view overdue loans) |
| **Graphical interface (Swing)** | `com.umb.taller.gui.LibraryGUI` | Desktop window with tabs for Books, Members and Loans (tables + forms) |

### How to Build and Run

**Option 1 — With Gradle (recommended, requires internet the first time to download JUnit 5 / AssertJ).**
This repo does not ship a Gradle wrapper; use a local Gradle 8.x+ installation, or generate
one with `gradle wrapper` if you prefer `./gradlew`:

```bash
gradle run           # Executes com.umb.taller.Main (fixed demo)
gradle runConsole     # Executes the interactive console menu (ConsoleMenu)
gradle runGui         # Executes the Swing graphical interface (LibraryGUI)
gradle test           # Runs the JUnit 5 test suite (LibraryServiceTest)
```

**Option 2 — Without Gradle or internet (only requires a JDK 17+):**

```bash
bash scripts/compile-and-run.sh     # Compiles and runs the Library demo (Main)
bash scripts/run-console.sh         # Compiles and runs the interactive console menu
bash scripts/run-gui.sh             # Compiles and runs the Swing graphical interface
bash scripts/run-simple-tests.sh    # Compiles and runs SimpleLibraryServiceTest (no external deps)
```

`SimpleLibraryServiceTest` mirrors the same cases as `LibraryServiceTest` (the real JUnit 5
suite) but is written as a plain `main` method with hand-rolled assertions, so it can be
compiled and run with nothing but `javac`/`java` — useful for environments (like GitHub
Codespaces sessions without outbound network) where Gradle cannot resolve the JUnit 5
dependency.

> Note: `LibraryGUI` needs a graphical desktop (X11/Wayland/Windows/macOS) to open a window.
> It will fail with a `HeadlessException` on a plain SSH/CI terminal without a display —
> use `ConsoleMenu` in those environments instead.

