package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PaymentRepository extends MongoRepository<Payment, Long> {

    @Query("{ 'loanId' : ?0, 'borrowerId' : ?1 }")
    List<Payment> findbyLoanIdAndBorrowerId(Long loanId, Long borrowerId);

    @Query("{ 'loanId' : ?0 }")
    Payment findByLoanId(Long loanId);
}
