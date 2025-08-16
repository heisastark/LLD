package DesignPatterns.Creational;

class Computer{
    String processor;
    int memory;
    int storage;
    String graphicsCard;

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }
}

interface ComputerBuilder{
    ComputerBuilder addProcessor(String processor);
    ComputerBuilder addMemory(int memory);
    ComputerBuilder addStorage(int storage);
    ComputerBuilder addGraphicsCard(String graphicsCard);
    Computer build();
}

class DesktopComputerBuilder implements ComputerBuilder{
    private Computer computer;

    DesktopComputerBuilder(){
        computer = new Computer();
    }

    @Override
    public ComputerBuilder addProcessor(String processor) {
        computer.setProcessor(processor);
        return this;
    }

    @Override
    public ComputerBuilder addMemory(int memory) {
        computer.setMemory(memory);
        return this;
    }

    @Override
    public ComputerBuilder addStorage(int storage) {
        computer.setStorage(storage);
        return this;
    }

    @Override
    public ComputerBuilder addGraphicsCard(String graphicsCard) {
        computer.setGraphicsCard(graphicsCard);
        return this;
    }

    public Computer build(){
        return computer;
    }
}

public class Builder {
    public static void main(String[] args){
        Computer desktop = new DesktopComputerBuilder()
                .addProcessor("Mac M1")
                .addMemory(3)
                .addStorage(500)
                .addGraphicsCard("NVIDIA RT4030")
                .build();

        System.out.println(desktop.graphicsCard);
        System.out.println(desktop.memory);
    }
}
