package edu.mapua.it211.lendingtracker.components;

import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    LoanService loanService;

//    @Value("${spring.cron.accruedInterestUpdate}")
//    private String cronUpdateAccruedInterest;

    @Scheduled(cron = "${spring.cron.accruedInterestUpdate}" )
    public void updateAccruedInterest() {
        List<Loan> loans = loanService.getAllLoans();
        loans.forEach(loan -> {
            logger.info("Updating accrued interest for loan ID: " + loan.getLoanId());
            loanService.updateAccruedInterest(loan.getLoanId());
        });
    }
}
