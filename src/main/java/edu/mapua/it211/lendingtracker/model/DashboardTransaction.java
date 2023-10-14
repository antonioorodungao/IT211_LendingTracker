package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("dashboardtransactions")
@Data
public class DashboardTransaction {
    @Id
    private Long transactionId;
    private String operation;
    private String source; //Loan, Payment, Dashboard
    private Long sourceId;
    private BigDecimal amount;
}
