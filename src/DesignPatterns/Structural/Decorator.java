package DesignPatterns.Structural;

interface BasePizza{
    public String type();
    public int cost();
}

class ChickenPizza implements BasePizza{
    @Override
    public String type() {
        return "Chicken Pizza";
    }

    @Override
    public int cost() {
        return 100;
    }
}

class VegPizza implements BasePizza{
    @Override
    public String type() {
        return "Veg Pizza";
    }

    @Override
    public int cost() {
        return 80;
    }
}

abstract class Topping implements BasePizza {
    int toppingCost;
    public abstract void setToppingCost(int toppingCost);
    public abstract int getToppingCost();
}

class PeriPeriTopping extends Topping{
    int toppingCost;
    BasePizza pizza;

    @Override
    public void setToppingCost(int toppingCost){
        this.toppingCost = toppingCost;
    }

    @Override
    public int getToppingCost(){
        return toppingCost;
    }

    public PeriPeriTopping(BasePizza pizza){
        this.pizza = pizza;
        setToppingCost(20);
    }


    @Override
    public String type() {
        return pizza.type() + " + Peri Peri";
    }

    @Override
    public int cost() {
        return pizza.cost() + getToppingCost();
    }
}

class OlivesTopping extends Topping{
    int toppingCost;
    BasePizza pizza;

    @Override
    public void setToppingCost(int toppingCost) {
        this.toppingCost = toppingCost;
    }

    public OlivesTopping(BasePizza pizza){
        this.pizza = pizza;
        setToppingCost(50);
    }

    @Override
    public int getToppingCost() {
        return toppingCost;
    }

    @Override
    public String type() {
        return pizza.type() + " + Olives";
    }

    @Override
    public int cost() {
        return pizza.cost() + getToppingCost();
    }
}



public class Decorator {
    public static void main(String[] args){
        BasePizza pizza = new VegPizza();
        pizza = new PeriPeriTopping(pizza);
        pizza = new OlivesTopping(pizza);

        //Print Pizza Type with Base & Toppings
        System.out.println(pizza.type());

        //Print Total Pizza Cost with Base & Toppings
        System.out.println(pizza.cost());
    }
}
