package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.exceptions.PaymentException;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    @Autowired
    private LoanService loanService;

    @Autowired
    private DashboardService dashboardService;

    @Value("${transactionsTest}")
    private Boolean transactionsTest;

    public void deleteAll(){
        paymentRepository.deleteAll();
    }

    public void deletePayment(Long id) throws NotEnoughLoanableAmount {
        dashboardService.revertPayment(id);
        paymentRepository.deleteById(id);
    }

    public List<Payment> findLoanIdAndBorrowerId(Long loanId, Long borrowerId){
        return paymentRepository.findbyLoanIdAndBorrowerId(loanId, borrowerId);
    }

    @Transactional
    public void save(Payment payment, Boolean preload) throws RuntimeException {
        dashboardService.registerPayment(payment);
        payment.setPaymentId(mongoSequenceGenerator.generateSequence("sequencepaymentid"));
        payment.setPaymentDate(LocalDate.now());
        paymentRepository.save(payment);
        if(transactionsTest && !preload){
            throw new RuntimeException("Testing Failed payment transaction");
        }
        loanService.reduceLoanBalance(payment);
        loanService.addToEarnedInterest(payment);
        loanService.updateAccruedInterest(payment.getLoanId());
    }
}
