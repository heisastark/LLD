package DesignPatterns.Creational;

class Controller{
    private static Controller instance;
    private Controller(){

    }
    public static Controller getInstance(){
        if(instance==null){
            System.out.println("Creating new instance, since it doesn't exist!");
            instance=new Controller();
        }
        return instance;
    }
}

public class Singleton {
    public static void main(String[] args){
        Controller c1 = Controller.getInstance();
        Controller c2 = Controller.getInstance();
        Controller c3 = Controller.getInstance();
    }
}
