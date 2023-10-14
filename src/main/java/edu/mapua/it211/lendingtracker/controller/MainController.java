package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import edu.mapua.it211.lendingtracker.service.DashboardService;
import edu.mapua.it211.lendingtracker.service.DashboardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    DashboardService dashboardService;

    @Autowired
    DashboardTransactionService dashboardTransactionService;


    @GetMapping("/home")
    public String showHomePage(Model model){
        System.out.println("Redirecting to home.");
        Dashboard ds = dashboardService.getDashboard();
        List<DashboardTransaction> transactions = dashboardTransactionService.showLastTwentyTransactions();
        model.addAttribute("dashboard", ds);
        model.addAttribute("transactions", transactions);
        return "index";
    }
}
