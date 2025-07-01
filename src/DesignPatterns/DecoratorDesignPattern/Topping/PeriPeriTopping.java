package DesignPatterns.DecoratorDesignPattern.Topping;

import DesignPatterns.DecoratorDesignPattern.Pizza.BasePizza;

public class PeriPeriTopping extends ToppingDecorator{
    BasePizza pizza;
    public PeriPeriTopping(BasePizza pizza) {
        this.pizza = pizza;
    }

    @Override
    public int cost(){
       return pizza.cost() + 20;
    }

    @Override
    public String type() {
        return pizza.type() + " + Peri Peri";
    }
}
