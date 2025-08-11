import DesignPatterns.Decorator.Pizza.BasePizza;
import DesignPatterns.Decorator.Pizza.ChickenPizza;
import DesignPatterns.Decorator.Topping.PeriPeriTopping;
import DesignPatterns.StrategyDesignPattern.NormalVehicle;
import DesignPatterns.StrategyDesignPattern.SportsVehicle;
import DesignPatterns.StrategyDesignPattern.Vehicle;

public class Decorator {
    public static void main(String[] args) {
        BasePizza pizza = new ChickenPizza();
        pizza = new PeriPeriTopping(pizza);

        System.out.println(pizza.type());
        System.out.println(pizza.cost());
    }
}