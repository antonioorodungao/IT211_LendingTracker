package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;
import java.util.List;


public interface LoanRepository extends MongoRepository<Loan, Long> {
    @Query("{ 'borrowerId' : ?0 }")
    List<Loan> findLoansByBorrowerId(String id);

    @Query("{ 'loanId' : ?0 }")
    @Update("{$set: {'principal': ?1}}")
    void updatePrincipal(Long id, BigDecimal amount);

}
