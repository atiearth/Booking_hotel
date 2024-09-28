package com.cp.project.demo.service;

import org.springframework.stereotype.Service;

import com.cp.project.demo.model.forBooking.Booking;
import com.cp.project.demo.model.forStrategy.ConcreteStrategy;
import com.cp.project.demo.model.forStrategy.PaymentStrategy;
import com.cp.project.demo.repository.BookingRepository;

@Service
public class StrategyService {
    private final BookingRepository bookingRepository;

    public StrategyService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void selectPaymentMethod(Integer bookingId, String method, String details) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        PaymentStrategy paymentStrategy;

        switch (method.toLowerCase()) {
            case "creditcard":
                paymentStrategy = new ConcreteStrategy.CreditCardPayment(details);
                break;
            case "cash":
                paymentStrategy = new ConcreteStrategy.CashPayment(Double.parseDouble(details));
                break;
            default:
                throw new IllegalArgumentException("Invalid payment method: " + method);
        }

        booking.setPaymentStrategy(paymentStrategy);
        booking.processPayment();
        bookingRepository.save(booking); // บันทึกสถานะการชำระเงิน
    }
}
