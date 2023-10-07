package edu.mapua.it211.lendingtracker.exceptions;

public class DebtorNotFoundException extends Throwable {
    public DebtorNotFoundException(String message) {
        super(message);
    }
}
