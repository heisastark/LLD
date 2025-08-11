import DesignPatterns.Decorator.Pizza.BasePizza;
import DesignPatterns.Decorator.Pizza.ChickenPizza;
import DesignPatterns.Decorator.Topping.PeriPeriTopping;
import DesignPatterns.StrategyDesignPattern.NormalVehicle;
import DesignPatterns.StrategyDesignPattern.SportsVehicle;
import DesignPatterns.StrategyDesignPattern.Vehicle;

public class Decorator {
    public static void main(String[] args) {
        //Select a Pizza (Veg/Chicken)
        BasePizza pizza = new ChickenPizza();

        //Select a Topping
        pizza = new PeriPeriTopping(pizza);

        //Type concatenates Chicken + Peri Peri
        System.out.println(pizza.type());

        //Cost calculates Total Cost
        System.out.println(pizza.cost());
    }
}