package edu.mapua.it211.lendingtracker.exceptions;

public class BalanceIsNotZeroException extends Exception{
    @Override
    public String toString() {
        return this.getClass().toString() + ":" + "Balance is not zero";
    }
}
