package com.umb.taller.service;

import com.umb.taller.domain.Book;
import com.umb.taller.domain.Library;
import com.umb.taller.domain.Member;
import com.umb.taller.domain.Loan;
import com.umb.taller.exception.BusinessRuleException;
import com.umb.taller.exception.EntityNotFoundException;
import com.umb.taller.exception.ValidationException;

public class LibraryService {
    private final Library library;

    public LibraryService(Library library) {
        this.library = library;
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
}
