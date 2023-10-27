package edu.mapua.it211.lendingtracker.exceptions;

public class PaymentException extends Exception{

    public PaymentException(String message){
        super(message);
    }

    @Override
    public String toString() {
        return "PaymentException: " + "Exception is encountered during payment.";
    }
}
