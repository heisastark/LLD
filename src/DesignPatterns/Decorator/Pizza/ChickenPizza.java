package DesignPatterns.Decorator.Pizza;

public class ChickenPizza extends BasePizza{
    @Override
    public int cost() {
        return 120;
    }

    @Override
    public String type() {
        return "Chicken";
    }
}
