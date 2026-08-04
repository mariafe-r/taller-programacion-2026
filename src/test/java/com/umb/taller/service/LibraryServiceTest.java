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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThatThrownBy(() -> libraryService.loanBook("0000000000000", member.getMemberId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the member does not exist")
    void shouldThrowEntityNotFoundExceptionWhenMemberDoesNotExist() {
        assertThatThrownBy(() -> libraryService.loanBook(book.getIsbn(), "M-999"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Member not found");
    }

    @Test
    @DisplayName("should throw ValidationException when book id is blank")
    void shouldThrowValidationExceptionWhenBookIdIsBlank() {
        assertThatThrownBy(() -> libraryService.loanBook("   ", member.getMemberId()))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Book id is required");
    }

    @Test
    @DisplayName("should throw ValidationException when member id is blank")
    void shouldThrowValidationExceptionWhenMemberIdIsBlank() {
        assertThatThrownBy(() -> libraryService.loanBook(book.getIsbn(), "   "))
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining("Member id is required");
    }

    @Test
    @DisplayName("should throw BusinessRuleException when the book is not available")
    void shouldThrowBusinessRuleExceptionWhenBookIsNotAvailable() {
        book.setAvailable(false);

        assertThatThrownBy(() -> libraryService.loanBook(book.getIsbn(), member.getMemberId()))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Book is not available");
    }

    @Test
    @DisplayName("should loan a book successfully when the book exists")
    void shouldLoanBookSuccessfullyWhenBookExists() {
        Loan loan = libraryService.loanBook(book.getIsbn(), member.getMemberId());

        assertThat(loan).isNotNull();
        assertThat(loan.getBook()).isEqualTo(book);
        assertThat(loan.getMember()).isEqualTo(member);
        assertThat(library.getLoans()).hasSize(1);
        assertThat(member.getBorrowedBooks()).contains(book);
        assertThat(book.isAvailable()).isFalse();
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the book does not exist")
    void shouldThrowEntityNotFoundExceptionWhenBookDoesNotExistForLoaning() {
        assertThatThrownBy(() -> libraryService.loanBook("9789999999999", member.getMemberId()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Book not found");
    }
}
