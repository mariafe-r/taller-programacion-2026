package com.umb.taller;

import com.umb.taller.domain.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("📚 Bienvenido al Sistema de Biblioteca");
        
        // Ejemplo de uso
        try {
            // Crear autores
            Author author1 = new Author("J.K. Rowling", "jk@email.com", "123456789", 
                "British author, best known for Harry Potter series");
            
            Author author2 = new Author("Gabriel García Márquez", "gabo@email.com", "987654321",
                "Colombian novelist and Nobel laureate");
            
            // Crear libros
            Book book1 = new Book("Harry Potter and the Philosopher's Stone", 
                author1, "9780747532699", 1997);
            Book book2 = new Book("Cien Años de Soledad", 
                author2, "9788437604947", 1967);
            Book book3 = new Book("Harry Potter and the Chamber of Secrets", 
                author1, "9780439064866", 1998);
            
            // Crear biblioteca
            Library library = new Library("Biblioteca UMB");
            library.addBook(book1);
            library.addBook(book2);
            library.addBook(book3);
            
            // Crear miembro
            Member member = new Member("Carlos Pérez", "carlos@umb.edu.co", "3101234567", "M001");
            library.registerMember(member);
            
            System.out.println("📚 Biblioteca: " + library.getName());
            System.out.println("📖 Libros disponibles: " + library.getAvailableBooks().size());
            System.out.println("👤 Miembros registrados: " + library.getMembers().size());
            
            // Mostrar libros disponibles
            System.out.println("\n📖 Libros disponibles:");
            library.getAvailableBooks().forEach(book -> 
                System.out.println("  - " + book.getTitle() + " por " + book.getAuthor().getName())
            );
            
            // Realizar un préstamo
            System.out.println("\n📤 Realizando préstamo...");
            Loan loan = library.loanBook(book1, member);
            System.out.println("✅ Préstamo realizado: " + book1.getTitle());
            
            // Mostrar libros prestados
            System.out.println("\n📚 Libros prestados por " + member.getName() + ":");
            member.getBorrowedBooks().forEach(book -> 
                System.out.println("  - " + book.getTitle())
            );
            
            // Devolver libro
            System.out.println("\n📥 Devolviendo libro...");
            if (!member.getBorrowedBooks().isEmpty()) {
                library.returnBook(loan);
                System.out.println("✅ Libro devuelto: " + book1.getTitle());
            }
            
            System.out.println("\n🎯 Sistema funcionando correctamente!");
            
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
