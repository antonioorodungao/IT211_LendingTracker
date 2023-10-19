package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.model.Loan;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.service.LoanService;
import edu.mapua.it211.lendingtracker.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    PaymentService paymentService;

    @GetMapping("/loan/view")
    public String viewLoans(@RequestParam("borrowerId") Long borrowerId, @RequestParam("loanId") Long loanId, Model model) {
        Loan loan = loanService.getLoan(loanId);
        model.addAttribute("loan", loan);
        Payment newPayment = new Payment();
        newPayment.setLoanId(loanId);
        newPayment.setBorrowerId(borrowerId);
        model.addAttribute("newPayment", newPayment);
        List<Payment> payments = paymentService.findLoanIdAndBorrowerId(loanId, borrowerId);

        model.addAttribute("payments", payments);
        model.addAttribute("pageTitle", "View Loan Payments (ID: " + loanId + ")");
        return "viewloan";
    }

    @PostMapping("/loan/add")
    public String addLoan(Loan loan, RedirectAttributes ra) {
        loanService.save(loan);
        ra.addAttribute("id", loan.getBorrowerId());
        ra.addFlashAttribute("message", "The loan has been added.");
        return "redirect:/borrower/view";
    }
}
