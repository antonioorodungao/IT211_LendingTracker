package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Borrower;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface BorrowerRepository extends MongoRepository<Borrower, Long>{

    @Query("{ 'borrowerId' : ?0 }")
    @Update("{$set: {'status' : ?1 }}")
    void setDebtorStatusClosed(Long id, String status);

}
