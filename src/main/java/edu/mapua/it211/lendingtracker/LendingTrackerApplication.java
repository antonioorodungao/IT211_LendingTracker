package edu.mapua.it211.lendingtracker;

import edu.mapua.it211.lendingtracker.model.Debtor;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.DebtorRepository;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import edu.mapua.it211.lendingtracker.service.DebtorService;
import edu.mapua.it211.lendingtracker.service.LoanService;
import edu.mapua.it211.lendingtracker.service.PaymentService;
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
    DebtorService debtorService;

    @Autowired
    LoanService loanService;

    @Autowired
    PaymentService paymentService;

    public static void main(String[] args) {
        SpringApplication.run(LendingTrackerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        debtorService.deleteAll();
        loanService.deleteAll();
        addLenderProfiles();
    }

    void addLenderProfiles() {
        debtorService.save(new Debtor("Antonio", "Dungao", "antoniodungao@yahoo.com", "09123456789"));
        debtorService.save(new Debtor("John", "Doe", "johndoe@yahoo.com", "09123456789"));
        debtorService.save(new Debtor("Jane", "Doe", "janedoe@yahoo.com", "09123456789"));

        Loan loan = new Loan();
        loan.setBalance(new BigDecimal(1000));
        loan.setBorrowerId("2023AntDun");
        loan.setPrincipal(new BigDecimal(1000));
        loan.setBalance(new BigDecimal(1000));
        loan.setDatePromised(LocalDate.now().plusDays(7));
        loan.setDateBorrowed(LocalDate.now());

        loanService.save(loan);

        loan = new Loan();
        loan.setBalance(new BigDecimal(1000));
        loan.setBorrowerId("2023JohDoe");
        loan.setPrincipal(new BigDecimal(1000));
        loan.setBalance(new BigDecimal(1000));
        loan.setDatePromised(LocalDate.now().plusDays(7));
        loan.setDateBorrowed(LocalDate.now());
        loanService.save(loan);

        Payment p = new Payment();
        p.setInterestPayment(new BigDecimal(100));
        p.setInterestPayment(new BigDecimal(100));
        p.setPaymentDate(LocalDate.now());
        p.setBorrowerId(loan.getBorrowerId());
        p.setLoanId(loan.getLoanId());
        paymentService.save(p);
    }
}
