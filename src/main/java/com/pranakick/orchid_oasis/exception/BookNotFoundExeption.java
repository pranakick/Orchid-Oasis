package com.pranakick.orchid_oasis.exception;

// This class is used to throw an exception when a book is not found.
public class BookNotFoundExeption extends RuntimeException{
    public BookNotFoundExeption(String message){
        super(message);
    }
}
