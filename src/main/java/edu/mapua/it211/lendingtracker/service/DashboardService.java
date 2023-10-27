package edu.mapua.it211.lendingtracker.service;


import edu.mapua.it211.lendingtracker.Utils;
import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.repository.DashboardRepository;
import edu.mapua.it211.lendingtracker.repository.LoanRepository;
import jakarta.annotation.PostConstruct;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static edu.mapua.it211.lendingtracker.Utils.Sources.*;

@Service
public class DashboardService {

    @Autowired
    DashboardRepository dashboardRepository;

    @Autowired
    DashboardTransactionService dashboardTransactionService;

    @Autowired
    LoanRepository loanRepository;

    public void initDashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setLoanableFund(new BigDecimal(5000));
        dashboard.setTotalRevenue(BigDecimal.ZERO);
        dashboard.setTotalInterest(BigDecimal.ZERO);
        dashboard.setDashboardId(1L);
        Dashboard d = dashboardRepository.save(dashboard);
    }

    public Dashboard getDashboard() {
        return dashboardRepository.find(1L);
    }

    public void deleteAll() {
        dashboardRepository.deleteAll();
        dashboardTransactionService.deleteAll();
    }

    public void addAmountToLoanableFund(BigDecimal additionalAmount) {
        Dashboard d = dashboardRepository.find(1L);
        dashboardRepository.updateLoanableFund(d.getLoanableFund().add(additionalAmount));
    }

    public void subtractAmountFromLoanableFund(BigDecimal additionalAmount) throws NotEnoughLoanableAmount {
        withdrawAmountFromLoanableFund(additionalAmount);
    }

    public void withdrawAmountFromLoanableFund(BigDecimal additionalAmount) throws NotEnoughLoanableAmount {
        Dashboard d = dashboardRepository.find(1L);
        if (d.getLoanableFund().subtract(additionalAmount).compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughLoanableAmount();
        }
        dashboardRepository.updateLoanableFund(d.getLoanableFund().subtract(additionalAmount));
    }

    public void addAmountToTotalRevenue(BigDecimal additionalAmount) {
        Dashboard d = dashboardRepository.find(1L);
        dashboardRepository.updateTotalRevenue(d.getTotalRevenue().add(additionalAmount));
    }

    @Transactional
    public void registerPayment(@NotNull Payment payment) {
        if (!Objects.isNull(payment.getInterestPayment()) && !payment.getInterestPayment().equals(BigDecimal.ZERO)) {
            DashboardTransaction dashboardTransaction = new DashboardTransaction();
            dashboardTransaction.setOperation("Interest Payment");
            dashboardTransaction.setSource(PAYMENT_INT.name());
            dashboardTransaction.setSourceId(payment.getPaymentId());
            dashboardTransaction.setAmount(payment.getInterestPayment());
            dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
            addAmountToLoanableFund(payment.getInterestPayment());
            addAmountToTotalRevenue(payment.getInterestPayment());
        }

        if (!Objects.isNull(payment.getPrincipalPayment()) && !payment.getPrincipalPayment().equals(BigDecimal.ZERO)) {
            DashboardTransaction dashboardTransaction = new DashboardTransaction();
            dashboardTransaction.setOperation("Principal Payment");
            dashboardTransaction.setSource(PAYMENT_PRI.name());
            dashboardTransaction.setSourceId(payment.getPaymentId());
            dashboardTransaction.setAmount(payment.getPrincipalPayment());
            dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
            addAmountToLoanableFund(payment.getPrincipalPayment());
        }
    }

    public void registerLoan(Loan loan) throws NotEnoughLoanableAmount {
        subtractAmountFromLoanableFund(loan.getPrincipal());
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setOperation("New Loan");
        dashboardTransaction.setSource(LOAN.name());
        dashboardTransaction.setSourceId(loan.getLoanId());
        dashboardTransaction.setAmount(loan.getPrincipal().negate());
        dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);

    }

    @Transactional
    public void revertPayment(Long id) throws NotEnoughLoanableAmount {
        DashboardTransaction dashboardTransaction = dashboardTransactionService.getDashboardTransaction(PAYMENT_PRI.name(), id);
        if (dashboardTransaction.getOperation().equals("Interest Payment")) {
            subtractAmountFromLoanableFund(dashboardTransaction.getAmount());
        } else if (dashboardTransaction.getOperation().equals("Principal Payment")) {
            subtractAmountFromLoanableFund(dashboardTransaction.getAmount());
        }
        dashboardTransactionService.deleteDashboardTransaction(dashboardTransaction.getTransactionId());
    }

    //get all lapsed loans
    public List<Loan> getLapsedLoans() {
        List<Loan> openLoans = loanRepository.findAllByStatus(Utils.LoanStatus.OPEN.toString());
        return openLoans.stream().filter(loan -> loan.getAccruedInterest().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());
    }
}
