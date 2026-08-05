package com.umb.taller.domain;

import java.time.LocalDate;

public class Loan {
    private Book book;
    private Member member;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(Book book, Member member) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }

        this.book = book;
        this.member = member;
        this.loanDate = LocalDate.now();
        this.returned = false;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public int calculateLateFee() {
        if (!returned || returnDate == null) {
            return 0;
        }

        long daysLate = java.time.temporal.ChronoUnit.DAYS.between(loanDate.plusDays(book.getMaxLoanDays()), returnDate);
        return daysLate > 0 ? (int) daysLate : 0;
    }

    public void returnItem() {
        if (returned) {
            throw new IllegalStateException("This loan has already been returned");
        }
        this.returnDate = LocalDate.now();
        this.returned = true;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book +
                ", member=" + member +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                '}';
    }
}
