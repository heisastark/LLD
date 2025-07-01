import DesignPatterns.StrategyDesignPattern.NormalVehicle;
import DesignPatterns.StrategyDesignPattern.SportsVehicle;
import DesignPatterns.StrategyDesignPattern.Vehicle;

public class Test {
    public static void main(String[] args) {
        Vehicle sportsCar = new SportsVehicle();
        sportsCar.drive();

        Vehicle car = new NormalVehicle();
        car.drive();

        Vehicle truck = new NormalVehicle();
        truck.drive();
    }
}