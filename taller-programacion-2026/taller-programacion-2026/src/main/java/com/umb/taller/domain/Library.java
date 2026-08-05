package com.umb.taller.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private String name;
    private List<Book> books;
    private List<Member> members;
    private List<Loan> loans;

    public Library(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Library name cannot be empty");
        }
        this.name = name.trim();
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!books.contains(book)) {
            books.add(book);
        }
    }

    public void removeBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.remove(book);
    }

    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        if (!members.contains(member)) {
            members.add(member);
        }
    }

    public void removeMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.remove(member);
    }

    public Loan loanBook(Book book, Member member) {
        if (book == null || member == null) {
            throw new IllegalArgumentException("Book and member cannot be null");
        }
        if (!books.contains(book)) {
            throw new IllegalArgumentException("Book is not in the library");
        }
        if (!members.contains(member)) {
            throw new IllegalArgumentException("Member is not registered in the library");
        }

        member.borrowBook(book);
        Loan loan = new Loan(book, member);
        loans.add(loan);
        return loan;
    }

    public void returnBook(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        if (!loans.contains(loan)) {
            throw new IllegalArgumentException("Loan is not registered in the library");
        }

        loan.getMember().returnBook(loan.getBook());
        loan.returnItem();
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public List<Loan> getLoansByMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        return loans.stream()
                .filter(loan -> loan.getMember().equals(member))
                .collect(Collectors.toList());
    }

    public List<Loan> getOverdueLoans() {
        LocalDate today = LocalDate.now();
        return loans.stream()
                .filter(loan -> !loan.isReturned())
                .filter(loan -> {
                    LocalDate dueDate = loan.getLoanDate().plusDays(loan.getBook().getMaxLoanDays());
                    return today.isAfter(dueDate);
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", books=" + books.size() +
                ", members=" + members.size() +
                ", loans=" + loans.size() +
                '}';
    }
}
