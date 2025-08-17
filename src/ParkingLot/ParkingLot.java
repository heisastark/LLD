package ParkingLot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

enum VehicleType{
    TWO_WHEELER,
    FOUR_WHEELER
}

class Vehicle{
    String vehicleNo;
    VehicleType type;

    public Vehicle(String vehicleNo, VehicleType type){
        this.vehicleNo = vehicleNo;
        this.type = type;
    }
}

class ParkingSpace{
    boolean parked;
    String name;

    ParkingSpace(String name){
        parked = false;
        this.name = name;
    }

    void park(){
        parked = true;
    }

    void free(){
        parked = false;
    }
}

class ParkingSpaceManager{
    List<ParkingSpace> parkingSpaceList;
    ParkingSpaceManager(){
        parkingSpaceList = new ArrayList<>();
        parkingSpaceList.add(new ParkingSpace("100"));
        parkingSpaceList.add(new ParkingSpace("101"));
        parkingSpaceList.add(new ParkingSpace("102"));
        parkingSpaceList.add(new ParkingSpace("103"));
        parkingSpaceList.add(new ParkingSpace("104"));
        parkingSpaceList.add(new ParkingSpace("105"));
    }

    public ParkingSpace findParkingSpace(){
        for(ParkingSpace parkingSpace: parkingSpaceList){
            if(!parkingSpace.parked)
                return parkingSpace;
        }
        return null;
    }
}

class Ticket{
    Date date;
    Vehicle vehicle;
    ParkingSpace parkingSpace;

    public Ticket(Date date, Vehicle vehicle, ParkingSpace parkingSpace){
        this.date = date;
        this.vehicle = vehicle;
        this.parkingSpace = parkingSpace;
    }

    long getEntryTime(){
        return date.getTime();
    }
}

class EntryGate{
    private static EntryGate entryGate;
    private ParkingSpaceManager parkingSpaceManager;

    private EntryGate(){
        parkingSpaceManager = new ParkingSpaceManager();
    }

    public static EntryGate getInstance(){
        if(entryGate == null){
            entryGate = new EntryGate();
        }
        return entryGate;
    }

    public Ticket generateTicket(Vehicle vehicle){
        ParkingSpace parkingSpace = parkingSpaceManager.findParkingSpace();
        parkingSpace.park();
        return new Ticket(new Date(), vehicle, parkingSpace);
    }
}

interface PricingStrategy{
    long getCost();
    long calculateCost(long elapsedTimeInMilliseconds);
}

class HourlyPricingStrategy implements PricingStrategy{
    @Override
    public long getCost() {
        return 3000;
    }

    @Override
    public long calculateCost(long elapsedTimeInMilliseconds) {
        return getCost() * TimeUnit.MILLISECONDS.toHours(elapsedTimeInMilliseconds);
    }
}

class MinutePricingStrategy implements PricingStrategy{
    @Override
    public long getCost() {
        return 50;
    }

    @Override
    public long calculateCost(long elapsedTimeInMilliseconds) {
        return getCost() * TimeUnit.MILLISECONDS.toMinutes(elapsedTimeInMilliseconds);
    }
}

class SecondsPricingStrategy implements PricingStrategy{
    @Override
    public long getCost() {
        return 1;
    }

    @Override
    public long calculateCost(long elapsedTimeInMilliseconds) {
        return getCost() * TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMilliseconds);
    }
}

class ExitGate{
    private static ExitGate exitGate;
    private PricingStrategy pricingStrategy;

    private ExitGate(){
        pricingStrategy = new SecondsPricingStrategy();
    }

    public static ExitGate getInstance(){
        if(exitGate == null){
            exitGate = new ExitGate();
        }
        return exitGate;
    }

    long calculateCost(Ticket ticket){
        long elapsedTimeInMilliseconds = new Date().getTime() - ticket.getEntryTime();
        return pricingStrategy.calculateCost(elapsedTimeInMilliseconds);
    }
}

class Flow{
    Ticket ticket;
    public void runVehicleEntryFlow(){
        //ParkingLot.Vehicle is at the Entry Gate of the Parking Lot
        Vehicle vehicle = new Vehicle("TG07C8888", VehicleType.FOUR_WHEELER);
        EntryGate entryGate = EntryGate.getInstance();
        ticket = entryGate.generateTicket(vehicle);
    }

    public void runVehicleExitFlow(Ticket ticket){
        try {
            Thread.sleep(4000);
        }
        catch (Exception e){
            System.out.println("An exception occured:" + e);
        }
        ExitGate exitGate = ExitGate.getInstance();
        long cost = exitGate.calculateCost(ticket);
        System.out.println("Please pay Rs." + cost + "/-");
    }

    public void runEntireFlow(){
        runVehicleEntryFlow();
        runVehicleExitFlow(ticket);
    }
}


public class ParkingLot {
    public static void main(String[] args){
        Flow flow = new Flow();
        flow.runEntireFlow();
    }
}
