package com.umb.taller;

import com.umb.taller.domain.Author;
import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Loan;
import com.umb.taller.domain.Member;
import com.umb.taller.service.LibraryService;
import com.umb.taller.validation.ValidationExample;

public class Main {
    public static void main(String[] args) {
        System.out.println(" Bienvenido al Sistema de Biblioteca");

        try {
            ValidationExample.main(new String[0]);

            Author author1 = new Author("J.K. Rowling", "jk@email.com", "123456789",
                    "British author, best known for Harry Potter series");
            Author author2 = new Author("Gabriel García Márquez", "gabo@email.com", "987654321",
                    "Colombian novelist and Nobel laureate");

            Book book1 = new Book("Harry Potter and the Philosopher's Stone",
                    author1, "9780747532699", 1997);
            Book book2 = new Book("Cien Años de Soledad",
                    author2, "9788437604947", 1967);
            Book book3 = new Book("Harry Potter and the Chamber of Secrets",
                    author1, "9780439064866", 1998);

            Library library = new Library("Biblioteca UMB");
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);

            Member member = new Member("Carlos Pérez", "carlos@umb.edu.co", "3101234567", "M001");
            library.registerMember(member);

            LibraryService service = new LibraryService(library);

            System.out.println(" Biblioteca: " + library.getName());
            System.out.println(" Libros disponibles: " + library.getAvailableBooks().size());
            System.out.println(" Miembros registrados: " + library.getMembers().size());

            System.out.println("\n Libros disponibles:");
            library.getAvailableBooks().forEach(book ->
                    System.out.println("  - " + book.getTitle() + " por " + book.getAuthor().getName())
            );

            System.out.println("\n Realizando préstamo con el servicio...");
            Loan loan = service.loanBook(book1.getIsbn(), member.getMemberId());
            System.out.println(" Préstamo realizado: " + book1.getTitle());

            System.out.println("\n Libros prestados por " + member.getName() + ":");
            member.getBorrowedBooks().forEach(book ->
                    System.out.println("  - " + book.getTitle())
            );

            System.out.println("\n Devolviendo libro...");
            library.returnBook(loan);
            System.out.println(" Libro devuelto: " + book1.getTitle());

            System.out.println("\n Préstamos vencidos: " + library.getOverdueLoans().size());
            System.out.println(" Sistema funcionando correctamente!");

        } catch (Exception e) {
            System.err.println(" Error: " + e.getMessage());
        }
    }
}
