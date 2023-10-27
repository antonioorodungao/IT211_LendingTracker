package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.exceptions.NotEnoughLoanableAmount;
import edu.mapua.it211.lendingtracker.exceptions.PaymentException;
import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment/add")
    public String addPayment(Payment payment, RedirectAttributes ra) {

        try {
            paymentService.save(payment, false);
            ra.addFlashAttribute("message", "The payment has been added.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("error", "There is an error during payment.");
        }
        ra.addAttribute("borrowerId", payment.getBorrowerId());
        ra.addAttribute("loanId", payment.getLoanId());

        return "redirect:/loan/view";
    }

    @GetMapping("/payment/delete")
    public String deletePayment(@RequestParam Long paymentId, @RequestParam Long borrowerId, @RequestParam Long loanId, RedirectAttributes ra) {
        ra.addAttribute("borrowerId", borrowerId);
        ra.addAttribute("loanId", loanId);
        try {
            paymentService.deletePayment(paymentId);
            ra.addFlashAttribute("message", "The payment has been deleted.");
        } catch (NotEnoughLoanableAmount e) {
            ra.addFlashAttribute("error", e.toString());
        }

        return "redirect:/loan/view";
    }
}
