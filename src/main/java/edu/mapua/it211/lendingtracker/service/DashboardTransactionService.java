package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.components.MongoSequenceGenerator;
import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.DashboardTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DashboardTransactionService {

    @Autowired
    DashboardTransactionRepository dashboardTransactionRepository;


    @Autowired
    MongoSequenceGenerator mongoSequenceGenerator;



    public void saveDashboardTransactions(DashboardTransaction dashboardTransaction) {
        dashboardTransaction.setTransactionId(mongoSequenceGenerator.generateSequence("dashboardtransactionid"));
        dashboardTransactionRepository.save(dashboardTransaction);
    }

    public void addFund(BigDecimal amount){
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setAmount(amount);
        dashboardTransaction.setOperation("Add Fund");
        dashboardTransaction.setSource("Dashboard");
        saveDashboardTransactions(dashboardTransaction);
    }

    public void withdrawFund(BigDecimal amount){
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setAmount(amount.negate());
        dashboardTransaction.setOperation("Withdraw Fund");
        dashboardTransaction.setSource("Dashboard");
        saveDashboardTransactions(dashboardTransaction);
    }

    public void deleteAll(){
        dashboardTransactionRepository.deleteAll();
    }

    public List<DashboardTransaction> showLastTwentyTransactions(){
        return dashboardTransactionRepository.findTop20ByOrderByTransactionIdDesc();
    }
}
