package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DashboardTransactionRepository extends MongoRepository<DashboardTransaction, Long> {

     DashboardTransaction getDashboardTransactionsByTransactionId(Long id);

     List<DashboardTransaction> findTop20ByOrderByTransactionIdDesc();
     @Query("{ 'source' : ?0, 'sourceId' : ?1 }")
    DashboardTransaction findBySourceAndTransactionId(String source, Long sourceId);
}
