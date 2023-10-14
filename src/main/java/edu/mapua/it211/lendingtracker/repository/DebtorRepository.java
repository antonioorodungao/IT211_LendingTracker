package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface DebtorRepository extends MongoRepository<Borrower, String>{

    @Query("{ 'borrowerId' : ?0 }")
    @Update("{$set: {'status' : 'Inactive' }}")
    void setDebtorStatusClosed(String id);

}
