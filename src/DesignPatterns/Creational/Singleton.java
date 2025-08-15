package DesignPatterns.Creational;

class Controller{
    private static Controller instance;
    private Controller(){

    }
    public static Controller getInstance(){
        if(instance==null){
            System.out.println("Creating new controller instance, since it doesn't exist!");
            instance=new Controller();
        }
        return instance;
    }
}

class ThreadController{
    private static volatile ThreadController instance;
    private ThreadController(){

    }
    public static ThreadController getInstance(){
        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null){
                    System.out.println("Creating new thread controller instance, since it doesn't exist!");
                    instance=new ThreadController();
                }
            }
        }
        return instance;
    }
}

public class Singleton {
    public static void main(String[] args){
        Controller c1 = Controller.getInstance();
        Controller c2 = Controller.getInstance();
        Controller c3 = Controller.getInstance();

        ThreadController tc1 = ThreadController.getInstance();
        ThreadController tc2 = ThreadController.getInstance();
    }
}
