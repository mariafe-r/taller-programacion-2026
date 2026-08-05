package com.umb.taller.exception;

public class BookNotFoundException extends DomainException {
    public BookNotFoundException(String bookId) {
        super("No se encontró el libro con id: " + bookId);
    }
}
