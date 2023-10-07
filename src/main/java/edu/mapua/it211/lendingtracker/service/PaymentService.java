package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private MongoSequenceGenerator mongoSequenceGenerator;

    public void deleteAll(){
        paymentRepository.deleteAll();
    }

    public void deletePayment(Long id){
        paymentRepository.deleteById(id);
    }

    public List<Payment> findLoanIdAndBorrowerId(Long loanId, String borrowerId){
        return paymentRepository.findbyLoanIdAndBorrowerId(loanId, borrowerId);
    }

    public void save(Payment payment){
        payment.setPaymentId(mongoSequenceGenerator.generateSequence("sequencepaymentid"));
        payment.setPaymentDate(LocalDate.now());
        paymentRepository.save(payment);
    }

}
