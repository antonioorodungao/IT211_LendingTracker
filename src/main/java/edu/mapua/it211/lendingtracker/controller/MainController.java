package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.model.Dashboard;
import edu.mapua.it211.lendingtracker.model.DashboardTransaction;
import edu.mapua.it211.lendingtracker.model.Fund;
import edu.mapua.it211.lendingtracker.service.DashboardService;
import edu.mapua.it211.lendingtracker.service.DashboardTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
Fund f = new Fund();
        model.addAttribute("dashboard", ds);
        model.addAttribute("transactions", transactions);
        model.addAttribute("fund", f);
        return "index";
    }

    @PostMapping("/dashboard/addFund")
    public String addFund(Fund fund, RedirectAttributes ra){
        dashboardService.addAmountToLoanableFund(fund.getAmount());
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setAmount(fund.getAmount());
        dashboardTransaction.setOperation("Add Fund");
        dashboardTransaction.setSource("Dashboard");
        dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
        ra.addFlashAttribute("message", "The fund has been added.");
        return "redirect:/home";
    }

    @PostMapping("/dashboard/withdrawFund")
    public String withdrawFund(Fund fund, RedirectAttributes ra){
        dashboardService.withdrawAmountFromLoanableFund(fund.getAmount());
        DashboardTransaction dashboardTransaction = new DashboardTransaction();
        dashboardTransaction.setAmount(fund.getAmount());
        dashboardTransaction.setOperation("Withdraw Fund");
        dashboardTransaction.setSource("Dashboard");
        dashboardTransactionService.saveDashboardTransactions(dashboardTransaction);
        ra.addFlashAttribute("message", "The fund has been withdrawn.");
        return "redirect:/home";
    }
}
