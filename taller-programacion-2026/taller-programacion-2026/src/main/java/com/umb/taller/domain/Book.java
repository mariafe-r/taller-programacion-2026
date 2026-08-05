package com.umb.taller.domain;

public class Book implements Loanable {
    private String title;
    private Author author;
    private String isbn;
    private int yearPublished;
    private boolean isAvailable;
    private int maxLoanDays;

    public Book() {
        this.isAvailable = true;
        this.maxLoanDays = 14;
    }

    public Book(String title, Author author, String isbn, int yearPublished) {
        this();
        setTitle(title);
        setAuthor(author);
        setIsbn(isbn);
        setYearPublished(yearPublished);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title.trim();
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || !isbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be a valid ISBN-13 with 13 digits");
        }
        this.isbn = isbn;
    }

    public int getYearPublished() {
        return yearPublished;
    }

    public void setYearPublished(int yearPublished) {
        if (yearPublished <= 0) {
            throw new IllegalArgumentException("Year published must be greater than 0");
        }
        this.yearPublished = yearPublished;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public int getMaxLoanDays() {
        return maxLoanDays;
    }

    public void setMaxLoanDays(int maxLoanDays) {
        if (maxLoanDays <= 0) {
            throw new IllegalArgumentException("Maximum loan days must be greater than 0");
        }
        this.maxLoanDays = maxLoanDays;
    }

    @Override
    public void borrow() {
        if (!isAvailable) {
            throw new IllegalStateException("Book is already borrowed");
        }
        this.isAvailable = false;
    }

    @Override
    public void returnItem() {
        if (isAvailable) {
            throw new IllegalStateException("Book is already available");
        }
        this.isAvailable = true;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", isbn='" + isbn + '\'' +
                ", yearPublished=" + yearPublished +
                ", isAvailable=" + isAvailable +
                ", maxLoanDays=" + maxLoanDays +
                '}';
    }
}