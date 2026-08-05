package com.umb.taller.domain;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person {
    private String memberId;
    private boolean active;
    private List<Book> borrowedBooks;
    private int maxBooksAllowed;

    public Member() {
        this.borrowedBooks = new ArrayList<>();
        this.active = true;
        this.maxBooksAllowed = 3;
    }

    public Member(String name, String email, String phone, String memberId) {
        this();
        setName(name);
        setEmail(email);
        setPhone(phone);
        setMemberId(memberId);
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be empty");
        }
        this.memberId = memberId.trim();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public int getMaxBooksAllowed() {
        return maxBooksAllowed;
    }

    public void setMaxBooksAllowed(int maxBooksAllowed) {
        if (maxBooksAllowed <= 0) {
            throw new IllegalArgumentException("Maximum books allowed must be greater than 0");
        }
        this.maxBooksAllowed = maxBooksAllowed;
    }

    public void borrowBook(Book book) {
        if (!active) {
            throw new IllegalStateException("Member is not active");
        }
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available");
        }
        if (borrowedBooks.size() >= maxBooksAllowed) {
            throw new IllegalStateException("Member has reached the maximum number of borrowed books");
        }
        borrowedBooks.add(book);
        book.borrow();
    }

    public void returnBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!borrowedBooks.remove(book)) {
            throw new IllegalStateException("The member did not borrow this book");
        }
        book.returnItem();
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", active=" + active +
                ", borrowedBooks=" + borrowedBooks +
                ", maxBooksAllowed=" + maxBooksAllowed +
                '}';
    }
}
