package DesignPatterns.Factory;

public class ShapeFactory {
    public Shape getShape(String input){
        switch (input){
            case "circle": return new Circle();
            case "rectangle": return new Rectangle();
            default: return null;
        }
    }
}
