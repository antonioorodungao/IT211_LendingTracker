package edu.mapua.it211.lendingtracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("dashboard")
@Data
public class Dashboard {
    @Id
    private Long dashId;
    private BigDecimal cashOnHand; //amount available for loaning
    private BigDecimal outstandingLoans; //total amount handed
    private BigDecimal currentMonthRevenue; //total interest this month
    private BigDecimal totalRevenue; //total interest since inception
}
