package edu.mapua.it211.lendingtracker.controller;

import edu.mapua.it211.lendingtracker.model.Payment;
import edu.mapua.it211.lendingtracker.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/payment/add")
    public String addPayment(Payment payment, RedirectAttributes ra) {

        paymentService.save(payment);
        ra.addAttribute("borrowerId", payment.getBorrowerId());
        ra.addAttribute("loanId", payment.getLoanId());
        return "redirect:/loan/view";
    }
}
