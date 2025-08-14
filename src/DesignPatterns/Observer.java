package DesignPatterns;

import java.util.ArrayList;
import java.util.List;

interface StockObserver{
    void update();
}

class Investor implements StockObserver{
    private String name;
    public Investor(String name){
        this.name = name;
    }

    @Override
    public void update() {
        System.out.println(name + " received a stock update!");
    }
}

class Agent implements StockObserver{
    private String agentID;
    public Agent(String agentID){
        this.agentID = agentID;
    }

    @Override
    public void update() {
        System.out.println(agentID + " agent received a stock update!");
    }
}

interface StockMarket{
    void addObserver(StockObserver o);
    void removeObserver(StockObserver o);
    void notifyObservers();
}

class StockMarketImpl implements StockMarket{
    private List<StockObserver> observerList = new ArrayList<>();
    @Override
    public void addObserver(StockObserver o) {
        observerList.add(o);
    }

    @Override
    public void removeObserver(StockObserver o) {
        observerList.remove(o);
    }

    @Override
    public void notifyObservers() {
        observerList.forEach((o)->{
            o.update();
        });
    }
}

public class Observer {
    public static void main(String[] args){
        Investor yash = new Investor("yash");
        Investor richi = new Investor("richi");
        Agent circuit = new Agent("57821");

        StockMarketImpl stockMarket = new StockMarketImpl();
        stockMarket.addObserver(yash);
        stockMarket.addObserver(richi);
        stockMarket.addObserver(circuit);
        stockMarket.notifyObservers();
    }
}
