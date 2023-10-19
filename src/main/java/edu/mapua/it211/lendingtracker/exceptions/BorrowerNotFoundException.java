package edu.mapua.it211.lendingtracker.exceptions;

public class BorrowerNotFoundException extends Exception {

    @Override
    public String toString() {
        return "BorrowerNotFoundException: " + "Borrower not found.";
    }

    public BorrowerNotFoundException(String message) {
        super(message);
    }
}
