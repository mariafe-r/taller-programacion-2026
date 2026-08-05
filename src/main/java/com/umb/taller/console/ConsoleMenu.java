package com.umb.taller.console;

import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Loan;
import com.umb.taller.domain.Member;
import com.umb.taller.service.LibraryService;

import java.util.List;
import java.util.Scanner;

/**
 * Menú interactivo de consola para operar el Sistema de Gestión de
 * Biblioteca sin necesidad de una interfaz gráfica. Solo depende de
 * {@link LibraryService} (nunca del dominio directamente), y no requiere
 * ninguna librería externa ni conexión a internet.
 */
public class ConsoleMenu {
    private final LibraryService libraryService;
    private final Scanner scanner;

    public ConsoleMenu(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Library library = new Library("Biblioteca UMB");
        LibraryService service = new LibraryService(library);
        seedSampleData(service);
        new ConsoleMenu(service).run();
    }

    private static void seedSampleData(LibraryService service) {
        try {
            service.addBook("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780747532699", 1997);
            service.addBook("Cien Años de Soledad", "Gabriel García Márquez", "9788437604947", 1967);
            service.addBook("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064866", 1998);
            service.registerMember("Carlos Pérez", "carlos@umb.edu.co", "3101234567", "M001");
        } catch (RuntimeException e) {
            System.err.println("⚠️  No se pudieron cargar los datos de ejemplo: " + e.getMessage());
        }
    }

    public void run() {
        System.out.println("=========================================================");
        System.out.println(" 📚 Sistema de Gestión de Biblioteca - " + libraryService.getLibraryName());
        System.out.println("=========================================================");

        boolean exit = false;
        while (!exit) {
            printMenu();
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1": listBooks(); break;
                case "2": addBook(); break;
                case "3": listMembers(); break;
                case "4": registerMember(); break;
                case "5": loanBook(); break;
                case "6": returnBook(); break;
                case "7": listActiveLoans(); break;
                case "8": listOverdueLoans(); break;
                case "0":
                    exit = true;
                    System.out.println("👋 ¡Hasta luego!");
                    break;
                default:
                    System.out.println("⚠️  Opción no válida.");
            }
        }
        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n--- Menú Principal ---");
        System.out.println("1. Listar libros");
        System.out.println("2. Agregar libro");
        System.out.println("3. Listar miembros");
        System.out.println("4. Registrar miembro");
        System.out.println("5. Prestar libro");
        System.out.println("6. Devolver libro");
        System.out.println("7. Ver préstamos activos");
        System.out.println("8. Ver préstamos vencidos");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void listBooks() {
        List<Book> books = libraryService.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("📭 No hay libros registrados.");
            return;
        }
        System.out.println("\n📚 Libros registrados (" + books.size() + "):");
        for (Book book : books) {
            System.out.printf("  [%s] %-45s | %-25s | %d | %s%n",
                    book.getIsbn(), book.getTitle(), book.getAuthor().getName(),
                    book.getYearPublished(), book.isAvailable() ? "Disponible" : "Prestado");
        }
    }

    private void addBook() {
        try {
            System.out.print("Título: ");
            String title = scanner.nextLine();
            System.out.print("Autor: ");
            String author = scanner.nextLine();
            System.out.print("ISBN (13 dígitos): ");
            String isbn = scanner.nextLine().trim();
            System.out.print("Año de publicación: ");
            int year = Integer.parseInt(scanner.nextLine().trim());

            Book book = libraryService.addBook(title, author, isbn, year);
            System.out.println("✅ Libro agregado: " + book.getTitle());
        } catch (NumberFormatException e) {
            System.out.println("❌ El año de publicación debe ser un número entero.");
        } catch (RuntimeException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listMembers() {
        List<Member> members = libraryService.getAllMembers();
        if (members.isEmpty()) {
            System.out.println("📭 No hay miembros registrados.");
            return;
        }
        System.out.println("\n👤 Miembros registrados (" + members.size() + "):");
        for (Member member : members) {
            System.out.printf("  [%s] %-25s | %-25s | %d/%d libros prestados%n",
                    member.getMemberId(), member.getName(), member.getEmail(),
                    member.getBorrowedBooks().size(), member.getMaxBooksAllowed());
        }
    }

    private void registerMember() {
        try {
            System.out.print("Nombre: ");
            String name = scanner.nextLine();
            System.out.print("Correo: ");
            String email = scanner.nextLine();
            System.out.print("Teléfono: ");
            String phone = scanner.nextLine();
            System.out.print("ID de miembro: ");
            String memberId = scanner.nextLine().trim();

            Member member = libraryService.registerMember(name, email, phone, memberId);
            System.out.println("✅ Miembro registrado: " + member.getName());
        } catch (RuntimeException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void loanBook() {
        try {
            System.out.print("ISBN del libro a prestar: ");
            String isbn = scanner.nextLine().trim();
            System.out.print("ID del miembro: ");
            String memberId = scanner.nextLine().trim();

            Loan loan = libraryService.loanBook(isbn, memberId);
            System.out.println("✅ Préstamo realizado: \"" + loan.getBook().getTitle()
                    + "\" → " + loan.getMember().getName() + " (fecha: " + loan.getLoanDate() + ")");
        } catch (RuntimeException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void returnBook() {
        List<Loan> activeLoans = libraryService.getActiveLoans();
        if (activeLoans.isEmpty()) {
            System.out.println("📭 No hay préstamos activos.");
            return;
        }

        System.out.println("\n📤 Préstamos activos:");
        for (int i = 0; i < activeLoans.size(); i++) {
            Loan loan = activeLoans.get(i);
            System.out.printf("  [%d] \"%s\" prestado a %s (desde %s)%n",
                    i + 1, loan.getBook().getTitle(), loan.getMember().getName(), loan.getLoanDate());
        }
        System.out.print("Seleccione el número del préstamo a devolver (0 para cancelar): ");

        try {
            int index = Integer.parseInt(scanner.nextLine().trim());
            if (index == 0) {
                return;
            }
            Loan loan = activeLoans.get(index - 1);
            libraryService.returnBook(loan);
            System.out.println("✅ Libro devuelto: " + loan.getBook().getTitle());
        } catch (NumberFormatException e) {
            System.out.println("❌ Debe ingresar un número.");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("❌ Selección inválida.");
        } catch (RuntimeException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    private void listActiveLoans() {
        List<Loan> loans = libraryService.getActiveLoans();
        if (loans.isEmpty()) {
            System.out.println("📭 No hay préstamos activos.");
            return;
        }
        System.out.println("\n📖 Préstamos activos (" + loans.size() + "):");
        for (Loan loan : loans) {
            System.out.printf("  \"%s\" → %s (desde %s)%n",
                    loan.getBook().getTitle(), loan.getMember().getName(), loan.getLoanDate());
        }
    }

    private void listOverdueLoans() {
        List<Loan> loans = libraryService.getOverdueLoans();
        if (loans.isEmpty()) {
            System.out.println("✅ No hay préstamos vencidos.");
            return;
        }
        System.out.println("\n⏰ Préstamos vencidos (" + loans.size() + "):");
        for (Loan loan : loans) {
            System.out.printf("  \"%s\" → %s (prestado el %s, máx. %d días)%n",
                    loan.getBook().getTitle(), loan.getMember().getName(),
                    loan.getLoanDate(), loan.getBook().getMaxLoanDays());
        }
    }
}
