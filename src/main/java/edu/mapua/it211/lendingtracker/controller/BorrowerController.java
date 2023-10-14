package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.exceptions.DebtorNotFoundException;
import edu.mapua.it211.lendingtracker.model.Borrower;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.service.DebtorService;
import edu.mapua.it211.lendingtracker.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BorrowerController {

    @Autowired
    private DebtorService debtorService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/borrower/new")
    public String addDebtor(Model model) {
        model.addAttribute("borrower", new Borrower());
        model.addAttribute("pagetTitle", "New Debtor");
        return "addborrower";
    }

    @PostMapping("/borrower/save")
    public String saveDebtor(Borrower borrower, RedirectAttributes ra) {
        debtorService.save(borrower);
        ra.addFlashAttribute("message", "The borrower has been added.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/borrower/deactivate")
    public String deactivate(@RequestParam("id") String id, RedirectAttributes ra) {
        debtorService.deleteDebtor(id);
        ra.addFlashAttribute("message", "The debtor has been deactivated.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/borrower/edit")
    public String showEditForm(@RequestParam("id") String id, Model model, RedirectAttributes ra) {
        try {
            Borrower borrower = debtorService.get(id);
            model.addAttribute("debtor", borrower);
            model.addAttribute("pageTitle", "Edit Lender (ID: " + id + ")");
            return "addborrower";
        } catch (DebtorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtor";
        }
    }

    @GetMapping("/borrower/view")
    public String showDebtor(@RequestParam("id") String borrowerId, Model model, RedirectAttributes ra) {
        try {
            Borrower borrower = debtorService.get(borrowerId);
            List<Loan> debtorLoans= loanService.getLoans(borrowerId);
            model.addAttribute("borrower", borrower);
            model.addAttribute("borrowerLoans", debtorLoans);
            String pageTitle = "View Borrower (" + borrower.getFirstName() + " " + borrower.getLastName() + ")";
            model.addAttribute("pageTitle",pageTitle );
            Loan loan = new Loan();
            loan.setBorrowerId(borrowerId);
            model.addAttribute("newLoan", loan);
            return "viewdebtor";
        } catch (DebtorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtors";
        }
    }


    @GetMapping("/listdebtors")
    public String listLenders(Model model) {
        System.out.println("Debtor list");
        model.addAttribute("borrower", new Borrower());
        model.addAttribute("debtorlist", debtorService.listDebtors());
        return "listdebtors";
    }
}
