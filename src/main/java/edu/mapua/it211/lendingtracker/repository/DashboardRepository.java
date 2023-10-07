package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;

public interface DashboardRepository extends MongoRepository<Dashboard, Long> {


    Dashboard getDashboardByDashId();

    @Query("{ 'dashId' : ?0 }")
    @Update("{$set: { 'cashOnHand' : ?1 }}")
    void updateCashonHandByDashId(Long id, BigDecimal additionalAmount);

}
