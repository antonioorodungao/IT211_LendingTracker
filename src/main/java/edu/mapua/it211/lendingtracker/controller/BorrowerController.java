package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.exceptions.BorrowerNotFoundException;
import edu.mapua.it211.lendingtracker.model.Borrower;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.service.BorrowerService;
import edu.mapua.it211.lendingtracker.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BorrowerController {

    @Autowired
    private BorrowerService borrowerService;

    @Autowired
    private LoanService loanService;

//    @GetMapping("/borrower/new")
//    public String addDebtor(Model model) {
//        model.addAttribute("borrower", new Borrower());
//        model.addAttribute("pagetTitle", "New Debtor");
//        return "editborrower";
//    }

    @PostMapping("/borrower/save")
    public String saveDebtor(Borrower borrower, RedirectAttributes ra) {
        borrowerService.save(borrower);
        ra.addFlashAttribute("message", "The borrower has been added/updated.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/borrower/deactivate")
    public String deactivate(@RequestParam("id") Long id, RedirectAttributes ra) {
        borrowerService.closeBorrower(id);
        ra.addFlashAttribute("message", "The debtor has been deactivated.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/borrower/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model, RedirectAttributes ra) {
        try {
            Borrower borrower = borrowerService.get(id);
            model.addAttribute("borrower", borrower);
            model.addAttribute("pageTitle", "Edit Lender (ID: " + id + ")");
            return "editborrower";
        } catch (BorrowerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtor";
        }
    }

    @GetMapping("/borrower/view")
    public String showDebtor(@RequestParam("id") Long borrowerId, Model model, RedirectAttributes ra) {
        try {
            Borrower borrower = borrowerService.get(borrowerId);
            List<Loan> debtorLoans= loanService.getLoans(borrowerId);
            model.addAttribute("borrower", borrower);
            model.addAttribute("borrowerLoans", debtorLoans);
            String pageTitle = "View Borrower (" + borrower.getFirstName() + " " + borrower.getLastName() + ")";
            model.addAttribute("pageTitle",pageTitle );
            Loan loan = new Loan();
            loan.setBorrowerId(borrowerId);
            model.addAttribute("newLoan", loan);
            return "viewdebtor";
        } catch (BorrowerNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtors";
        }
    }


    @GetMapping("/listdebtors")
    public String listLenders(Model model) {
        System.out.println("Debtor list");
        model.addAttribute("borrower", new Borrower());
        model.addAttribute("debtorlist", borrowerService.listDebtors());
        return "listdebtors";
    }
}
