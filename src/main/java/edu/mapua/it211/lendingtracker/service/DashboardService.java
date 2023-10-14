package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.DashboardRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    DashboardTransactionService dashboardTransactionService;

    public void initDashboard(){
        Dashboard dashboard = new Dashboard();
        dashboard.setLoanableFund(new BigDecimal(5000));
        dashboard.setTotalRevenue(BigDecimal.ZERO);
        dashboard.setTotalInterest(BigDecimal.ZERO);
        dashboard.setDashboardId(1L);
        dashboardRepository.save(dashboard);
    }

    public Dashboard getDashboard(){
        return dashboardRepository.find(1L);
    }

    public void deleteAll(){
        dashboardRepository.deleteAll();
        dashboardTransactionService.deleteAll();
    }

    public void addAmountToLoanableFund(BigDecimal additionalAmount) {
        Dashboard d = dashboardRepository.find(1L);
        dashboardRepository.updateLoanableFund(d.getLoanableFund().add(additionalAmount));
    }

    public void subtractAmountFromLoanableFund(BigDecimal additionalAmount) {
        Dashboard d = dashboardRepository.find(1L);
        dashboardRepository.updateLoanableFund(d.getLoanableFund().subtract(additionalAmount));
    }

    public void withdrawAmountFromLoanableFund(BigDecimal additionalAmount) {
        Dashboard d = dashboardRepository.find(1L);
        dashboardRepository.updateLoanableFund(d.getLoanableFund().subtract(additionalAmount));
    }

    public void registerPayment(@NotNull Payment payment) {

        if(!Objects.isNull(payment.getInterestPayment()) && !payment.getInterestPayment().equals(BigDecimal.ZERO)){
            DashboardTransaction dashboardTransaction = new DashboardTransaction();
            dashboardTransaction.setOperation("Interest Payment");
            dashboardTransaction.setSource("Payment");
            dashboardTransaction.setSourceId(payment.getPaymentId());
            dashboardTransaction.setAmount(payment.getInterestPayment());
            dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
            addAmountToLoanableFund(payment.getInterestPayment());
        }

        if(!Objects.isNull(payment.getPrincipalPayment()) &&!payment.getPrincipalPayment().equals(BigDecimal.ZERO)){
            DashboardTransaction dashboardTransaction = new DashboardTransaction();
            dashboardTransaction.setOperation("Principal Payment");
            dashboardTransaction.setSource("Payment");
            dashboardTransaction.setSourceId(payment.getPaymentId());
            dashboardTransaction.setAmount(payment.getPrincipalPayment());
            dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
            addAmountToLoanableFund(payment.getPrincipalPayment());
        }
    }

    public void registerLoan(Loan loan) {
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setOperation("New Loan");
        dashboardTransaction.setSource("Loan");
        dashboardTransaction.setSourceId(loan.getLoanId());
        dashboardTransaction.setAmount(loan.getPrincipal());
        dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
        subtractAmountFromLoanableFund(loan.getPrincipal());
    }
}
