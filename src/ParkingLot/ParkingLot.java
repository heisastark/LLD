package ParkingLot;

import java.util.*;
import java.util.concurrent.TimeUnit;

enum VehicleType {
    TWO_WHEELER,
    FOUR_WHEELER
}

class Vehicle {
    String vehicleNo;
    VehicleType type;

    public Vehicle(String vehicleNo, VehicleType type) {
        this.vehicleNo = vehicleNo;
        this.type = type;
    }
}

class ParkingSpace {
    boolean parked;
    String id;
    VehicleType supportedType;

    ParkingSpace(String id, VehicleType supportedType) {
        this.id = id;
        this.supportedType = supportedType;
        this.parked = false;
    }

    void park() {
        parked = true;
    }

    void free() {
        parked = false;
    }
}

class ParkingSpaceManager {
    private final List<ParkingSpace> parkingSpaces;

    ParkingSpaceManager() {
        parkingSpaces = new ArrayList<>();
        // Example: 3 two-wheeler, 3 four-wheeler spots
        parkingSpaces.add(new ParkingSpace("T-100", VehicleType.TWO_WHEELER));
        parkingSpaces.add(new ParkingSpace("T-101", VehicleType.TWO_WHEELER));
        parkingSpaces.add(new ParkingSpace("T-102", VehicleType.TWO_WHEELER));
        parkingSpaces.add(new ParkingSpace("F-200", VehicleType.FOUR_WHEELER));
        parkingSpaces.add(new ParkingSpace("F-201", VehicleType.FOUR_WHEELER));
        parkingSpaces.add(new ParkingSpace("F-202", VehicleType.FOUR_WHEELER));
    }

    public ParkingSpace findParkingSpace(VehicleType type) {
        for (ParkingSpace ps : parkingSpaces) {
            if (!ps.parked && ps.supportedType == type) {
                return ps;
            }
        }
        return null; // No available space
    }
}

enum TicketStatus {
    ACTIVE,
    CLOSED
}

class Ticket {
    UUID ticketId;
    Date entryTime;
    Date exitTime;
    Vehicle vehicle;
    ParkingSpace parkingSpace;
    long amount;
    TicketStatus status;

    public Ticket(Date entryTime, Vehicle vehicle, ParkingSpace parkingSpace) {
        this.ticketId = UUID.randomUUID();
        this.entryTime = entryTime;
        this.vehicle = vehicle;
        this.parkingSpace = parkingSpace;
        this.status = TicketStatus.ACTIVE;
    }

    long getEntryTimeMillis() {
        return entryTime.getTime();
    }
}

class EntryGate {
    private static volatile EntryGate instance;
    private final ParkingSpaceManager parkingSpaceManager;
    private final Map<String, Ticket> activeTickets;

    private EntryGate() {
        parkingSpaceManager = new ParkingSpaceManager();
        activeTickets = new HashMap<>();
    }

    public static EntryGate getInstance() {
        if (instance == null) {
            synchronized (EntryGate.class) {
                if (instance == null) {
                    instance = new EntryGate();
                }
            }
        }
        return instance;
    }

    public Ticket generateTicket(Vehicle vehicle) throws Exception {
        ParkingSpace parkingSpace = parkingSpaceManager.findParkingSpace(vehicle.type);
        if (parkingSpace == null) {
            throw new Exception("No available parking space for " + vehicle.type);
        }
        parkingSpace.park();
        Ticket ticket = new Ticket(new Date(), vehicle, parkingSpace);
        activeTickets.put(vehicle.vehicleNo, ticket);
        return ticket;
    }

    public Ticket getTicket(String vehicleNo) {
        return activeTickets.get(vehicleNo);
    }

    public void closeTicket(String vehicleNo) {
        activeTickets.remove(vehicleNo);
    }
}

interface PricingStrategy {
    long calculateCost(long elapsedTimeInMillis);
}

class HourlyPricingStrategy implements PricingStrategy {
    private static final long COST_PER_HOUR = 3000;

    @Override
    public long calculateCost(long elapsedTimeInMillis) {
        long hours = Math.max(1, TimeUnit.MILLISECONDS.toHours(elapsedTimeInMillis));
        return hours * COST_PER_HOUR;
    }
}

class MinutePricingStrategy implements PricingStrategy {
    private static final long COST_PER_MIN = 50;

    @Override
    public long calculateCost(long elapsedTimeInMillis) {
        long mins = Math.max(1, TimeUnit.MILLISECONDS.toMinutes(elapsedTimeInMillis));
        return mins * COST_PER_MIN;
    }
}

class SecondsPricingStrategy implements PricingStrategy {
    private static final long COST_PER_SECOND = 1;

    @Override
    public long calculateCost(long elapsedTimeInMillis) {
        long secs = Math.max(1, TimeUnit.MILLISECONDS.toSeconds(elapsedTimeInMillis));
        return secs * COST_PER_SECOND;
    }
}

class ExitGate {
    private static volatile ExitGate instance;
    private PricingStrategy pricingStrategy;

    private ExitGate() {
        // Default strategy
        this.pricingStrategy = new SecondsPricingStrategy();
    }

    public static ExitGate getInstance() {
        if (instance == null) {
            synchronized (ExitGate.class) {
                if (instance == null) {
                    instance = new ExitGate();
                }
            }
        }
        return instance;
    }

    public void setPricingStrategy(PricingStrategy strategy) {
        this.pricingStrategy = strategy;
    }

    public long calculateCost(Ticket ticket) {
        long elapsedTimeInMillis = new Date().getTime() - ticket.getEntryTimeMillis();
        return pricingStrategy.calculateCost(elapsedTimeInMillis);
    }

    public void processExit(Ticket ticket) {
        ticket.exitTime = new Date();
        long cost = calculateCost(ticket);
        ticket.amount = cost;
        ticket.status = TicketStatus.CLOSED;

        // Free the parking space
        ticket.parkingSpace.free();

        // Remove ticket from active records
        EntryGate.getInstance().closeTicket(ticket.vehicle.vehicleNo);

        System.out.println("Receipt: ");
        System.out.println("Vehicle No: " + ticket.vehicle.vehicleNo);
        System.out.println("Entry Time: " + ticket.entryTime);
        System.out.println("Exit Time: " + ticket.exitTime);
        System.out.println("Parking Spot: " + ticket.parkingSpace.id);
        System.out.println("Amount to Pay: Rs." + ticket.amount + "/-");
    }
}

class Flow {
    Ticket ticket;

    public void runVehicleEntryFlow() throws Exception {
        Vehicle vehicle = new Vehicle("TG07C8888", VehicleType.FOUR_WHEELER);
        EntryGate entryGate = EntryGate.getInstance();
        ticket = entryGate.generateTicket(vehicle);
        System.out.println("Vehicle Entered: " + vehicle.vehicleNo + " | Spot: " + ticket.parkingSpace.id);
    }

    public void runVehicleExitFlow() throws InterruptedException {
        Thread.sleep(4000); // simulate parking duration
        ExitGate exitGate = ExitGate.getInstance();
        exitGate.setPricingStrategy(new SecondsPricingStrategy()); // configurable
        exitGate.processExit(ticket);
    }

    public void runEntireFlow() throws Exception {
        runVehicleEntryFlow();
        runVehicleExitFlow();
    }
}

public class ParkingLot {
    public static void main(String[] args) throws Exception {
        Flow flow = new Flow();
        flow.runEntireFlow();
    }
}