package com.umb.taller.service;

import com.umb.taller.domain.Author;
import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Member;
import com.umb.taller.domain.Loan;
import com.umb.taller.exception.BusinessRuleException;
import com.umb.taller.exception.EntityNotFoundException;
import com.umb.taller.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Fachada de aplicación sobre {@link Library}. Concentra las validaciones y
 * las operaciones que necesitan las capas de presentación (consola y GUI),
 * para que ninguna de ellas dependa directamente de las reglas internas del
 * dominio (principio de inversión de dependencias).
 */
public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
    }

    public String getLibraryName() {
        return library.getName();
    }

    public Loan loanBook(String bookId, String memberId) {
        if (bookId == null || bookId.isBlank()) {
            throw new ValidationException("Book id is required");
        }
        if (memberId == null || memberId.isBlank()) {
            throw new ValidationException("Member id is required");
        }

        Book book = library.getBooks().stream()
                .filter(b -> b.getIsbn().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + bookId));

        Member member = library.getMembers().stream()
                .filter(m -> m.getMemberId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Member not found: " + memberId));

        if (!book.isAvailable()) {
            throw new BusinessRuleException("Book is not available");
        }

        return library.loanBook(book, member);
    }

    public void returnBook(Loan loan) {
        if (loan == null) {
            throw new ValidationException("Loan is required");
        }
        library.returnBook(loan);
    }

    public Book addBook(String title, String authorName, String isbn, int yearPublished) {
        if (authorName == null || authorName.isBlank()) {
            throw new ValidationException("Author name is required");
        }
        String slug = authorName.trim().toLowerCase().replaceAll("[^a-z0-9]+", ".");
        Author author = new Author(authorName.trim(), slug + "@library.local", "N/A",
                "Autor registrado desde la interfaz de usuario");
        Book book = new Book(title, author, isbn, yearPublished);
        library.addBook(book);
        return book;
    }

    public Member registerMember(String name, String email, String phone, String memberId) {
        Member member = new Member(name, email, phone, memberId);
        library.registerMember(member);
        return member;
    }

    public List<Book> getAllBooks() {
        return library.getBooks();
    }

    public List<Book> getAvailableBooks() {
        return library.getAvailableBooks();
    }

    public List<Member> getAllMembers() {
        return library.getMembers();
    }

    public List<Loan> getAllLoans() {
        return library.getLoans();
    }

    public List<Loan> getActiveLoans() {
        return library.getLoans().stream()
                .filter(loan -> !loan.isReturned())
                .collect(Collectors.toList());
    }

    public List<Loan> getOverdueLoans() {
        return library.getOverdueLoans();
    }
}
