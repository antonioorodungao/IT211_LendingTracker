package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Debtor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

public interface DebtorRepository extends MongoRepository<Debtor, String>{

    @Query("{ 'borrowerId' : ?0 }")
    @Update("{$set: {'status' : 'Inactive' }}")
    void setDebtorStatusClosed(String id);

}
