package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("dashboard")
@Data
public class Dashboard {

    @Id
    private Long dashboardId;
    private BigDecimal loanableFund; //amount available for loaning
    private BigDecimal totalAmountLoaned; //total amount handed
    private BigDecimal totalInterest; //total interest
    private BigDecimal totalRevenue; //total interest since inception
}


