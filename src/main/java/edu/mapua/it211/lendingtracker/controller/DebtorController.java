package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.exceptions.DebtorNotFoundException;
import edu.mapua.it211.lendingtracker.model.Debtor;
import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.service.DebtorService;
import edu.mapua.it211.lendingtracker.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DebtorController {

    @Autowired
    private DebtorService debtorService;

    @Autowired
    private LoanService loanService;

    @GetMapping("/debtor/new")
    public String addDebtor(Model model) {
        model.addAttribute("debtor", new Debtor());
        model.addAttribute("pagetTitle", "New Debtor");
        return "adddebtor";
    }

    @PostMapping("/debtor/save")
    public String saveDebtor(Debtor debtor, RedirectAttributes ra) {
        debtorService.save(debtor);
        ra.addFlashAttribute("message", "The debtor has been saved.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/debtor/deactivate/{id}")
    public String deactivate(@PathVariable("id") String id, RedirectAttributes ra) {
        debtorService.deleteDebtor(id);
        ra.addFlashAttribute("message", "The debtor has been deactivated.");
        return "redirect:/listdebtors";
    }


    @GetMapping("/debtor/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model, RedirectAttributes ra) {
        try {
            Debtor debtor = debtorService.get(id);
            model.addAttribute("debtor", debtor);
            model.addAttribute("pageTitle", "Edit Lender (ID: " + id + ")");
            return "adddebtor";
        } catch (DebtorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtor";
        }
    }

    @GetMapping("/debtor/view/{id}")
    public String showDebtor(@PathVariable("id") String debtorId, Model model, RedirectAttributes ra) {
        try {
            Debtor debtor = debtorService.get(debtorId);
            List<Loan> debtorLoans= loanService.getLoans(debtorId);
            model.addAttribute("debtor", debtor);
            model.addAttribute("debtorLoans", debtorLoans);
            model.addAttribute("pageTitle", "View Debtor (ID: " + debtorId + ")");
            return "viewdebtor";
        } catch (DebtorNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/listdebtors";
        }
    }


    @GetMapping("/listdebtors")
    public String listLenders(Model model) {
        System.out.println("Debtor list");
        model.addAttribute("debtorlist", debtorService.listDebtors());
        return "listdebtors";
    }
}
