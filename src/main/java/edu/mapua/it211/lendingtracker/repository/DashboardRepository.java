package edu.mapua.it211.lendingtracker.repository;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardRepository extends MongoRepository<Dashboard, Long> {

    //db.dashboard.update({dashboardId: 1}, {$set: {loanableFund: 5000}})
    @Query("{}")
    @Update("{$set: { 'loanableFund' : ?0 }}")
    void updateLoanableFund(BigDecimal additionalAmount);

    //db.dashboard.update({dashboardId: 1}, {$set: {totalRevenue: 100}})
    @Query("{}")
    @Update("{$set: { 'totalRevenue' : ?0 }}")
    void updateTotalRevenue(BigDecimal additionalAmount);

    //db.dashboard.findOne({dashboardId: 1})
    @Query("{dashboardId: ?0}")
    Dashboard find(Long dashboardId);

    @Query("{}")
    @Update("{$set: { 'totalAmountLoaned' : ?0 }}")

    void updateTotalAmountLoaned(BigDecimal amount);
}
