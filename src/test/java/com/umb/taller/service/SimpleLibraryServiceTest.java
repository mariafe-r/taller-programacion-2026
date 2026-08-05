package com.umb.taller.service;

import com.umb.taller.domain.Author;
import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Member;
import com.umb.taller.exception.BusinessRuleException;
import com.umb.taller.exception.EntityNotFoundException;
import com.umb.taller.exception.ValidationException;

public class SimpleLibraryServiceTest {
    public static void main(String[] args) {
        int passed = 0;
        int failed = 0;

        try {
            Library library = new Library("Central Library");
            LibraryService service = new LibraryService(library);

            Author author = new Author("Jane Austen", "jane@example.com", "123456789", "British author");
            Book book = new Book("Pride and Prejudice", author, "9781234567890", 1813);
            Member member = new Member("Ana", "ana@example.com", "3001234567", "M-001");

            library.addBook(book);
            library.registerMember(member);

            try {
                service.loanBook("0000000000000", member.getMemberId());
                failed++;
            } catch (EntityNotFoundException ex) {
                passed++;
            }

            try {
                service.loanBook(book.getIsbn(), "M-999");
                failed++;
            } catch (EntityNotFoundException ex) {
                passed++;
            }

            try {
                service.loanBook("   ", member.getMemberId());
                failed++;
            } catch (ValidationException ex) {
                passed++;
            }

            try {
                service.loanBook(book.getIsbn(), "   ");
                failed++;
            } catch (ValidationException ex) {
                passed++;
            }

            book.setAvailable(false);
            try {
                service.loanBook(book.getIsbn(), member.getMemberId());
                failed++;
            } catch (BusinessRuleException ex) {
                passed++;
            }

            book.setAvailable(true);
            var loan = service.loanBook(book.getIsbn(), member.getMemberId());
            if (loan != null && loan.getBook().equals(book) && loan.getMember().equals(member)
                    && library.getLoans().size() == 1 && member.getBorrowedBooks().contains(book)
                    && !book.isAvailable()) {
                passed++;
            } else {
                failed++;
            }

            System.out.println("SimpleLibraryServiceTest: passed=" + passed + ", failed=" + failed);
            if (failed > 0) {
                throw new RuntimeException("Some simple tests failed");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}
