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
    void updateBalance(Long id, BigDecimal balance);

    //db.loan.update({'loanId': <param1>}, {$set: {'status': <param2>}})
    @Query("{ 'loanId' : ?0 }")
    @Update("{$set: {'status': ?1}}")
    void setLoanStatusClosed(Long id, String string);

    //db.loan.find({'loanId': <param1>})
    @Query("{ 'loanId' : ?0 }")
    Loan findLoanByLoanId(Long id);

    //db.loan.update({'loanId': <param1>}, {$set: {'accruedInterest': <param2>}})
    @Query("{ 'loanId' : ?0 }")
    @Update("{$set: {'earnedInterest': ?1}}")
    void updateEarnedInterest(Long loanId, BigDecimal newEarnedInterest );

    //db.loan.update({'loanId': <param1>}, {$set: {'accruedInterest': <param2>}})
    @Query("{ 'loanId' : ?0 }")
    @Update("{$set: {'accruedInterest': ?1}}")
    void updateAccruedInterest(Long loanId, BigDecimal accruedInterest);
}
