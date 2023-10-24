package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
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
    public String showHomePage(Model model) {
        System.out.println("Redirecting to home.");
        Dashboard ds = dashboardService.getDashboard();
        List<DashboardTransaction> transactions = dashboardTransactionService.showLastTwentyTransactions();
        Fund f = new Fund();
        model.addAttribute("dashboard", ds);
        model.addAttribute("transactions", transactions);
        model.addAttribute("fund", f);
        model.addAttribute("lapsedLoans", dashboardService.getLapsedLoans());
        return "index";
    }

    @PostMapping("/dashboard/addFund")
    public String addFund(Fund fund, RedirectAttributes ra) {
        dashboardService.addAmountToLoanableFund(fund.getAmount());
        dashboardTransactionService.addFund(fund.getAmount());
        ra.addFlashAttribute("message", "The fund has been added.");
        return "redirect:/home";
    }

    @PostMapping("/dashboard/withdrawFund")
    public String withdrawFund(Fund fund, RedirectAttributes ra) {
        try {
            dashboardService.withdrawAmountFromLoanableFund(fund.getAmount());
            dashboardTransactionService.withdrawFund(fund.getAmount());
            ra.addFlashAttribute("message", "The fund has been withdrawn.");
        } catch (NotEnoughLoanableAmount e) {
            ra.addFlashAttribute("error", e.toString());
        }

        return "redirect:/home";
    }
}
