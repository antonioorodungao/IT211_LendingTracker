package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    public List<Loan> getLoans(String id) {
        return loanRepository.findLoansByBorrowerId(id);
    }

    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    public void save(Loan loan) {
        loan.setLoanId(mongoSequenceGenerator.generateSequence("sequenceloanid"));
        loanRepository.save(loan);
    }

    public void deleteAll() {
        loanRepository.deleteAll();
    }
}
