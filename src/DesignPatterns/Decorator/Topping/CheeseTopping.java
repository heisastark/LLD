package DesignPatterns.Decorator.Topping;

import DesignPatterns.Decorator.Pizza.BasePizza;

public class CheeseTopping extends ToppingDecorator{
    BasePizza pizza;
    CheeseTopping(BasePizza pizza){
        this.pizza = pizza;
    }

    @Override
    public int cost() {
        return pizza.cost() + 10;
    }

    @Override
    public String type() {
        return pizza.type() + " + Cheese";
    }
}
