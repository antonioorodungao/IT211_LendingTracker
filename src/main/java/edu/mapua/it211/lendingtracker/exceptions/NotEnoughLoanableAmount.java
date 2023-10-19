package edu.mapua.it211.lendingtracker.exceptions;

public class NotEnoughLoanableAmount extends Exception{

    @Override
    public String toString() {
        return "NotEnoughLoanableAmount: " + "Not enough loanable amount.";
    }
}
