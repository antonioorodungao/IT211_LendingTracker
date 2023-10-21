package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Document("loans")
@Data
public class Loan {

    @DBRef
    private List<Payment> payments;

    @Id
    private Long loanId;
    private Long borrowerId;
    private BigDecimal principal;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private BigDecimal accruedInterest;
    private BigDecimal earnedInterest;
    private LocalDate dateLastPayment;
    private LocalDate dateBorrowed;
    private LocalDate dateDue;

    private String status;
}
