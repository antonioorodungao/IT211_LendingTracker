package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;

public interface DashboardRepository extends MongoRepository<Dashboard, Long> {


    @Query("{}")
    @Update("{$set: { 'loanableFund' : ?0 }}")
    void updateLoanableFund(BigDecimal additionalAmount);

    @Query("{dashboardId: ?0}")
    Dashboard find(Long dashboardId);
}
