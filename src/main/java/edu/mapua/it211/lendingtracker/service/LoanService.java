package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    @Autowired
    private DashboardService dashboardService;

    public List<Loan> getLoans(String id) {
        return loanRepository.findLoansByBorrowerId(id);
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public void save(Loan loan) {
        loan.setLoanId(mongoSequenceGenerator.generateSequence("sequenceloanid"));
        loan.setDateBorrowed(LocalDate.now());
        loanRepository.save(loan);
        dashboardService.registerLoan(loan);
    }

    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }
    public void reducePrincipal(Payment payment){
        if(!Objects.isNull( payment.getPrincipalPayment())) {
            Loan loan = loanRepository.findById(payment.getLoanId()).orElse(null);
            BigDecimal newBalance = loan.getPrincipal().subtract(payment.getPrincipalPayment());
            loanRepository.updatePrincipal(loan.getLoanId(), newBalance);
        }
    }

    public void deleteAll() {
        loanRepository.deleteAll();
    }
}
