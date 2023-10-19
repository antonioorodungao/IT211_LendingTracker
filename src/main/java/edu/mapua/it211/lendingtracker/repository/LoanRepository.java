package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;
import java.util.List;


public interface LoanRepository extends MongoRepository<Loan, Long> {

    //db.loan.find({'borrowerId': <param1>})
    @Query("{ 'borrowerId' : ?0 }")
    List<Loan> findLoansByBorrowerId(Long id);

    //db.loan.update({'loanId': <param1>}, {$set: {'balance': <param2>}})
    @Query("{ 'loanId' : ?0 }")
    @Update("{$set: {'balance': ?1}}")
    void updateBalance(Long id, BigDecimal amount);

}
