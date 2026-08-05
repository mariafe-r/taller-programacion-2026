package com.umb.taller.service;

import com.umb.taller.domain.Author;
import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Loan;
import com.umb.taller.domain.Member;
import com.umb.taller.exception.BusinessRuleException;
import com.umb.taller.exception.EntityNotFoundException;
import com.umb.taller.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("LibraryService Tests")
class LibraryServiceTest {

    private Library library;
    private LibraryService libraryService;
    private Book book;
    private Member member;

    @BeforeEach
    void setUp() {
        library = new Library("Central Library");
        libraryService = new LibraryService(library);

        Author author = new Author("Jane Austen", "jane@example.com", "123456789", "British author");
        book = new Book("Pride and Prejudice", author, "9781234567890", 1813);
        member = new Member("Ana", "ana@example.com", "3001234567", "M-001");

        library.addBook(book);
        library.registerMember(member);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the book does not exist")
    void shouldThrowEntityNotFoundExceptionWhenBookDoesNotExist() {
        Exception ex = assertThrows(EntityNotFoundException.class,
            () -> libraryService.loanBook("0000000000000", member.getMemberId()));
        assertTrue(ex.getMessage().contains("Book not found"));
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the member does not exist")
    void shouldThrowEntityNotFoundExceptionWhenMemberDoesNotExist() {
        Exception ex = assertThrows(EntityNotFoundException.class,
            () -> libraryService.loanBook(book.getIsbn(), "M-999"));
        assertTrue(ex.getMessage().contains("Member not found"));
    }

    @Test
    @DisplayName("should throw ValidationException when book id is blank")
    void shouldThrowValidationExceptionWhenBookIdIsBlank() {
        Exception ex = assertThrows(ValidationException.class,
            () -> libraryService.loanBook("   ", member.getMemberId()));
        assertTrue(ex.getMessage().contains("Book id is required"));
    }

    @Test
    @DisplayName("should throw ValidationException when member id is blank")
    void shouldThrowValidationExceptionWhenMemberIdIsBlank() {
        Exception ex = assertThrows(ValidationException.class,
            () -> libraryService.loanBook(book.getIsbn(), "   "));
        assertTrue(ex.getMessage().contains("Member id is required"));
    }

    @Test
    @DisplayName("should throw BusinessRuleException when the book is not available")
    void shouldThrowBusinessRuleExceptionWhenBookIsNotAvailable() {
        book.setAvailable(false);

        Exception ex = assertThrows(BusinessRuleException.class,
            () -> libraryService.loanBook(book.getIsbn(), member.getMemberId()));
        assertTrue(ex.getMessage().contains("Book is not available"));
    }

    @Test
    @DisplayName("should loan a book successfully when the book exists")
    void shouldLoanBookSuccessfullyWhenBookExists() {
        Loan loan = libraryService.loanBook(book.getIsbn(), member.getMemberId());
        assertNotNull(loan);
        assertEquals(book, loan.getBook());
        assertEquals(member, loan.getMember());
        assertEquals(1, library.getLoans().size());
        assertTrue(member.getBorrowedBooks().contains(book));
        assertFalse(book.isAvailable());
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the book does not exist")
    void shouldThrowEntityNotFoundExceptionWhenBookDoesNotExistForLoaning() {
        Exception ex = assertThrows(EntityNotFoundException.class,
            () -> libraryService.loanBook("9789999999999", member.getMemberId()));
        assertTrue(ex.getMessage().contains("Book not found"));
    }
}
