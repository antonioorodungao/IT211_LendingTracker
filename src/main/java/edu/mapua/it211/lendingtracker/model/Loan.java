package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

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

//    @Field(targetType = FieldType.DECIMAL128)
    private BigDecimal principal;

    //@Field(targetType = FieldType.DECIMAL128)
    private BigDecimal balance;

    //@Field(targetType = FieldType.DECIMAL128)
    private BigDecimal interestRate;

    private BigDecimal accruedInterest;

    private BigDecimal earnedInterest;

    private LocalDate dateLastPayment;
    private LocalDate dateBorrowed;
    private LocalDate dateDue;

    private String status;
}
