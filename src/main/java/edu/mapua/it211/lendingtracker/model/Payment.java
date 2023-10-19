package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document("payment")
@Data
public class Payment {
    @Id
    private Long paymentId;
    private Long borrowerId;
    private Long loanId;
    private BigDecimal interestPayment;
    private BigDecimal principalPayment;
    private LocalDate paymentDate;
}
