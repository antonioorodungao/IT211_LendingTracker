package edu.mapua.it211.lendingtracker;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.model.Borrower;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
@EnableMongoRepositories
public class LendingTrackerApplication implements CommandLineRunner {

    @Autowired
    BorrowerService borrowerService;

    @Autowired
    LoanService loanService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    DashboardService dashboardService;

    @Autowired
    DashboardTransactionService dashboardTransactionService;

    @Autowired
    MongoSequenceGenerator mongoSequenceGenerator;



    public static void main(String[] args) {
        SpringApplication.run(LendingTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        borrowerService.deleteAll();
        loanService.deleteAll();
        paymentService.deleteAll();
        dashboardTransactionService.deleteAll();
        dashboardService.deleteAll();
        mongoSequenceGenerator.resetAllSequence();
        dashboardService.initDashboard();
        addBorrowerProfiles();
        dashboardService.getLapsedLoans();
        System.out.println(dashboardService.getLapsedLoans());
    }

    void addBorrowerProfiles() throws NotEnoughLoanableAmount {
        Borrower b1 = borrowerService.save(new Borrower("Antonio", "Dungao", "antoniodungao@yahoo.com", "09123456789"));
        borrowerService.save(new Borrower("John", "Doe", "johndoe@yahoo.com", "09123456789"));
        borrowerService.save(new Borrower("Jane", "Doe", "janedoe@yahoo.com", "09123456789"));

        Loan loan = new Loan();
        loan.setBorrowerId(b1.getBorrowerId());
        loan.setBalance(new BigDecimal(1000));
        loan.setPrincipal(new BigDecimal(1000));
        loan.setInterestRate(new BigDecimal(10));
        loan.setAccruedInterest(new BigDecimal(0));
        loan.setEarnedInterest(new BigDecimal(0));
        loan.setBalance(new BigDecimal(1000));
        loan.setDateDue(LocalDate.now().plusDays(7));
        loan.setDateBorrowed(LocalDate.now().minusMonths(2));
        loan = loanService.newLoan(loan);

        Payment p = new Payment();
        p.setInterestPayment(new BigDecimal(100));
        p.setInterestPayment(new BigDecimal(100));
        p.setPaymentDate(LocalDate.now());
        p.setBorrowerId(loan.getBorrowerId());
        p.setLoanId(loan.getLoanId());
        paymentService.save(p);

        loan = new Loan();
        loan.setBorrowerId(b1.getBorrowerId());
        loan.setBalance(new BigDecimal(1000));
        loan.setPrincipal(new BigDecimal(1000));
        loan.setInterestRate(new BigDecimal(10));
        loan.setAccruedInterest(new BigDecimal(0));
        loan.setEarnedInterest(new BigDecimal(0));
        loan.setBalance(new BigDecimal(1000));
        loan.setDateDue(LocalDate.now().plusDays(7));
        loan.setDateBorrowed(LocalDate.now().minusMonths(4));
        loan = loanService.newLoan(loan);

        p = new Payment();
        p.setInterestPayment(new BigDecimal(100));
        p.setInterestPayment(new BigDecimal(100));
        p.setPaymentDate(LocalDate.now());
        p.setBorrowerId(loan.getBorrowerId());
        p.setLoanId(loan.getLoanId());
        paymentService.save(p);

    }
}
