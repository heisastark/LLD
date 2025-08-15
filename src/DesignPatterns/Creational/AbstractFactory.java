package DesignPatterns.Creational;

interface Phone{
    void switchOn();
}

interface Earphone{
    void connect();
}

class WiredPhone implements Phone{
    @Override
    public void switchOn() {
        System.out.println("Wired Phone turning on!");
    }
}

class BluetoothPhone implements Phone{
    @Override
    public void switchOn() {
        System.out.println("Bluetooth Phone turning on!");
    }
}

class WiredEarphone implements Earphone{
    @Override
    public void connect() {
        System.out.println("Wired earphone connecting!");
    }
}

class BluetoothEarphone implements Earphone{
    @Override
    public void connect() {
        System.out.println("Bluetooth earphone connecting!");
    }
}

interface GadgetsFactory{
    Phone createPhone();
    Earphone createEarphone();
}

class BluetoothGadgetsFactory implements GadgetsFactory{
    @Override
    public Phone createPhone() {
        return new BluetoothPhone();
    }

    @Override
    public Earphone createEarphone() {
        return new BluetoothEarphone();
    }
}

class WiredGadgetsFactory implements GadgetsFactory{
    @Override
    public Phone createPhone() {
        return new WiredPhone();
    }

    @Override
    public Earphone createEarphone() {
        return new WiredEarphone();
    }
}

class Manufacturer {
    public static void createGadget(GadgetsFactory gadgetsFactory){
        Phone phone = gadgetsFactory.createPhone();
        Earphone earphone = gadgetsFactory.createEarphone();
        phone.switchOn();
        earphone.connect();
    }
}

public class AbstractFactory {
    public static void main(String[] args){
        System.out.println("Creating Bluetooth Gadgets");
        Manufacturer.createGadget(new BluetoothGadgetsFactory());

        System.out.println("Creating Wired Gadgets");
        Manufacturer.createGadget(new WiredGadgetsFactory());
    }
}
