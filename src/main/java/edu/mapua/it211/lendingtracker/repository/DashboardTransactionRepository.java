package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface DashboardTransactionRepository extends MongoRepository<DashboardTransaction, Long> {
    //db.dashboardtransactions.findOne({transactionId: 1})
     DashboardTransaction getDashboardTransactionsByTransactionId(Long id);

     //db.dashboardtransactions.find().sort({transactionId: -1}).limit(20)
     List<DashboardTransaction> findTop20ByOrderByTransactionIdDesc();
     @Query("{ 'source' : ?0, 'sourceId' : ?1 }")
    DashboardTransaction findBySourceAndTransactionId(String source, Long sourceId);
}
