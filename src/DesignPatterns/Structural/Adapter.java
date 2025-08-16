package DesignPatterns.Structural;

interface Boat{
    void row();
}

interface Ship{
    void sail();
}

class SimpleRowingBoat implements Boat{
    @Override
    public void row() {
        System.out.println("Simple Rowing Boat is being rowed!");
    }
}

class PropellerShip implements Ship{
    @Override
    public void sail() {
        System.out.println("Sailing the ship using propellers!");
    }
}

class Captain{
    private Boat boat;

    Captain(Boat rowingBoat){
        this.boat = rowingBoat;
    }

    public void row(){
        boat.row();
    }
}

class ShipToBoatAdapter implements Boat{
    private final Ship ship;

    public ShipToBoatAdapter(){
        ship = new PropellerShip();
    }

    @Override
    public void row() {
        ship.sail();
    }
}

public class Adapter {
    public static void main(String[] args){
//        Captain captain = new Captain(new SimpleRowingBoat());
//        captain.row();

        Captain captain = new Captain(new ShipToBoatAdapter());
        captain.row();

    }
}
