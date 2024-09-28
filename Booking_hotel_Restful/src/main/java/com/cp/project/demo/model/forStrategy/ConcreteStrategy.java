package com.cp.project.demo.model.forStrategy;

public class ConcreteStrategy {
    
    public static class CreditCardPayment implements PaymentStrategy {
        private String cardNumber;

        public CreditCardPayment(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
        }

        public String getCardNumber() {
            return cardNumber;
        }
    }
    
    public static class CashPayment implements PaymentStrategy {
        private Double value;

        public CashPayment(Double value) {
            this.value = value;
        }

        @Override
        public void pay(double amount) {
            System.out.println("Paid " + amount + " using Cash: " + value);
        }


        public Double getValue() {
            return this.value;
        }
    }
}