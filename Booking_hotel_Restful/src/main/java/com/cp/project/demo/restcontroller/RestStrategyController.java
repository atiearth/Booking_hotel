package com.cp.project.demo.restcontroller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cp.project.demo.service.StrategyService;

@RestController
@RequestMapping("/api")
public class RestStrategyController {

    private  StrategyService paymentService;
    public RestStrategyController(StrategyService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public ResponseEntity<String> processPayment(@RequestParam Integer bookingId,@RequestParam String method, @RequestParam String details) {
        try {
            paymentService.selectPaymentMethod(bookingId,method, details);
            return ResponseEntity.ok("Payment processed successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing the payment.");
        }
    }
}
