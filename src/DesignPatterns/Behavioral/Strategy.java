package DesignPatterns.Behavioral;

interface PaymentStrategy{
    void processPayment();
}

class UPIPaymentStrategy implements PaymentStrategy{
    @Override
    public void processPayment() {
        System.out.println("Collecting payment through UPI.");
    }
}

class CreditCardPaymentStrategy implements PaymentStrategy{
    @Override
    public void processPayment() {
        System.out.println("Collecting payment through Credit Card.");
    }
}

class PaymentSystem{
    private PaymentStrategy paymentStrategy;
    public PaymentSystem(PaymentStrategy paymentStrategy){
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy p){
        paymentStrategy = p;
    }

    public void makePayment(){
        paymentStrategy.processPayment();
    }
}

public class Strategy {
    public static void main(String[] args){
        PaymentSystem paymentSystem = new PaymentSystem(new UPIPaymentStrategy());
        paymentSystem.makePayment();

        paymentSystem.setPaymentStrategy(new CreditCardPaymentStrategy());
        paymentSystem.makePayment();;
    }
}
