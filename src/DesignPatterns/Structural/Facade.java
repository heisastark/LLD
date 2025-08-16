package DesignPatterns.Structural;

class UserAuth{
    public void login(String username, String password){
        System.out.println("User logged in!");
    }
    public void logout(){
        System.out.println("User logged out!");
    }
}

class PaymentProcess{
    public void process(String paymentMethod){
        System.out.println("Payment Processed!");
    }
}

class InventoryManagement{
    public void update(String productID, int quantity){
        System.out.println("Inventory Updated!");
    }
}

class Order{
    public void place(String orderID){
        System.out.println("Order Placed!");
    }
}

class ECommFacade{
    private UserAuth userAuth;
    private PaymentProcess paymentProcess;
    private InventoryManagement inventoryManagement;
    private Order order;

    public ECommFacade(UserAuth userAuth, PaymentProcess paymentProcess, InventoryManagement inventoryManagement, Order order){
        this.userAuth = userAuth;
        this.paymentProcess = paymentProcess;
        this.inventoryManagement = inventoryManagement;
        this.order = order;
    }

    public void purchaseProduct(){
        userAuth.login("Ronald", "ronald123");
        order.place("75123");
        paymentProcess.process("UPI");
        inventoryManagement.update("a561eer", 5);
        userAuth.logout();
    }
}

public class Facade {
    public static void main(String[] args){
        ECommFacade eCommFacade = new ECommFacade(new UserAuth(), new PaymentProcess(), new InventoryManagement(), new Order());
        eCommFacade.purchaseProduct();
    }
}
