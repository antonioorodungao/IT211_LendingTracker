package edu.mapua.it211.lendingtracker.service;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.repository.DashboardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//@DataJdbcTest
class DashboardServiceTest {

//    @Autowired
//    DashboardRepository dashboardRepository;
//
//    @Mock
//    DashboardTransactionService dashboardTransactionService;
//    @Autowired
//    DashboardService dashboardService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }

//    @AfterEach
//    void cleanUp() {
//        dashboardRepository.deleteAll();
//    }

//    @Test
//    void addAmountToLoanableFund() {
//        Dashboard dashboard = new Dashboard();
//        dashboard.setLoanableFund(new BigDecimal(1000));
//        when(dashboardRepository.findOne()).thenReturn(dashboard);
//        dashboardService.addAmountToLoanableFund(new BigDecimal(1000));
//        verify(dashboardRepository).updateLoanableFund(new BigDecimal(2000));
//        assertEquals(new BigDecimal(2000), dashboard.getLoanableFund());
//    }

    @Test
    void subtractAmountFromLoanableFund() {
    }

    @Test
    void withdrawAmountFromLoanableFund() {
    }

    @Test
    void registerPayment() {
    }

    @Test
    void registerLoan() {
    }

    @Test
    void getLapsedLoans() {
    }
}