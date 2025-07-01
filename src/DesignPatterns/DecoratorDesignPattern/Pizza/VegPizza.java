package DesignPatterns.DecoratorDesignPattern.Pizza;

public class VegPizza extends BasePizza {
    @Override
    public int cost(){
        return 100;
    }

    @Override
    public String type() {
        return "Veg";
    }
}
