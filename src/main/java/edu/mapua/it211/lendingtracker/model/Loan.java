package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document("loans")
@Data
public class Loan {

    @Id
    private Long loanId;
    private Long borrowerId;
    private BigDecimal principal;
    private BigDecimal balance;
    private BigDecimal accruedInterest;
    private BigDecimal interestRate;
    private LocalDate dateBorrowed;
    private LocalDate dateDue;
    private String status;
}
