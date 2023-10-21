package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.exceptions.BalanceIsNotZeroException;
import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    public Loan newLoan(Loan loan) throws NotEnoughLoanableAmount {
        loan.setLoanId(mongoSequenceGenerator.generateSequence("sequenceloanid"));
        loan.setBalance(loan.getPrincipal());
        loan.setStatus(OPEN.toString());
        loan.setDateBorrowed(LocalDate.now());
        dashboardService.registerLoan(loan);
        return loanRepository.save(loan);
    }

    public Boolean closeLoan(Long id) throws BalanceIsNotZeroException {
        Loan loan = loanRepository.findLoanByLoanId(id);
        if(loan.getBalance().compareTo(BigDecimal.ZERO) == 0) {
            loanRepository.setLoanStatusClosed(id, CLOSED.name());
            return true;
        }else{
            throw new BalanceIsNotZeroException();
        }
    }
    public void reduceLoanBalance(Payment payment){
        if(!Objects.isNull( payment.getPrincipalPayment())) {
            Loan loan = loanRepository.findById(payment.getLoanId()).orElse(null);
            BigDecimal newBalance = loan.getBalance().subtract(payment.getPrincipalPayment());
            loanRepository.updateBalance(loan.getLoanId(), newBalance);
        }
    }


    public void deleteAll() {
        loanRepository.deleteAll();
    }
}
