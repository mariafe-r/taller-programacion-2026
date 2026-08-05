package com.umb.taller.exception;

public class BookAlreadyBorrowedException extends DomainException {
    public BookAlreadyBorrowedException(String bookId) {
        super("El libro con id " + bookId + " ya está prestado");
    }
}
