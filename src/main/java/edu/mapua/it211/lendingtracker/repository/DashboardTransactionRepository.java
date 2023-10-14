package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DashboardTransactionRepository extends MongoRepository<DashboardTransaction, Long> {

     DashboardTransaction getDashboardTransactionsByTransactionId(Long id);

     List<DashboardTransaction> findTop20ByOrderByTransactionIdDesc();
}
