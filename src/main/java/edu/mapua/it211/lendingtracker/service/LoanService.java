package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.exceptions.BalanceIsNotZeroException;
import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import static edu.mapua.it211.lendingtracker.Utils.LoanStatus.*;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    @Autowired
    private DashboardService dashboardService;

    public List<Loan> getLoans(Long id) {
        return loanRepository.findLoansByBorrowerId(id);
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Transactional
    public Loan newLoan(Loan loan) throws NotEnoughLoanableAmount {
        loan.setLoanId(mongoSequenceGenerator.generateSequence("sequenceloanid"));
        loan.setBalance(loan.getPrincipal());
        loan.setStatus(OPEN.toString());
        loan.setAccruedInterest(new BigDecimal(0));
        loan.setEarnedInterest(new BigDecimal(0));
        if(loan.getDateBorrowed() == null)
            loan.setDateBorrowed(LocalDate.now());
        dashboardService.registerLoan(loan);
        return loanRepository.save(loan);
    }

    public Boolean closeLoan(Long id) throws BalanceIsNotZeroException {
        Loan loan = loanRepository.findLoanByLoanId(id);
        if (loan.getBalance().compareTo(BigDecimal.ZERO) == 0) {
            loanRepository.setLoanStatusClosed(id, CLOSED.name());
            return true;
        } else {
            throw new BalanceIsNotZeroException();
        }
    }

    public void reduceLoanBalance(Payment payment) {
        if (!Objects.isNull(payment.getPrincipalPayment())) {
            Loan loan = loanRepository.findById(payment.getLoanId()).orElse(null);
            assert loan != null;
            BigDecimal newBalance = loan.getBalance().subtract(payment.getPrincipalPayment());
            loanRepository.updateBalance(loan.getLoanId(), newBalance);
        }
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    //get all loans
    //get loan by id
    //calculate the accruedInterest = no of months * (interestRate /100) * principal;
    public void updateAccruedInterest(Long LoanId) {
        Loan loan = loanRepository.findById(LoanId).orElse(null);
        assert loan != null;
        LocalDate currentDate = LocalDate.now();
        LocalDate borrowedDate = loan.getDateBorrowed();

        int months;
        if (currentDate.isAfter(borrowedDate)) {
            Period period = Period.between(borrowedDate, currentDate);
            months = (period.getYears() * 12) + period.getMonths();
        } else {
            months = 0;
        }

        if (months > 0) {
            BigDecimal interestRate = loan.getInterestRate().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
            BigDecimal totalInterest = interestRate.multiply(loan.getPrincipal()).multiply(BigDecimal.valueOf(months));
            BigDecimal accruedInterest = totalInterest.subtract(loan.getEarnedInterest());
            loanRepository.updateAccruedInterest(loan.getLoanId(), accruedInterest);
        }
    }


    public void deleteAll() {
        loanRepository.deleteAll();
    }

    public void addToEarnedInterest(Payment payment) {
        if (!Objects.isNull(payment.getInterestPayment())) {
            Loan loan = loanRepository.findById(payment.getLoanId()).orElse(null);
            BigDecimal newEarnedInterest = loan.getEarnedInterest().add(payment.getInterestPayment());
            loanRepository.updateEarnedInterest(loan.getLoanId(), newEarnedInterest);
        }
    }
}
