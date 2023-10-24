package edu.mapua.it211.lendingtracker;

import org.bson.types.Decimal128;

import java.math.BigDecimal;

public class Utils {

    public enum BorrowerStatus {
        OPEN, CLOSED
    }

    public enum LoanStatus {
        OPEN, CLOSED
    }

    public enum Sources {
        LOAN, PAYMENT_PRI, DASHBOARD, PAYMENT_INT
    }

    public Decimal128 convertToDecimal128(BigDecimal bigDecimal) {
        return new Decimal128(bigDecimal);
    }
}
