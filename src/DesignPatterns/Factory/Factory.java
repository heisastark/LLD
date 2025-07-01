package DesignPatterns.Factory;

public class Factory {
    public static void main(String args[]){
        Shape shape = new ShapeFactory().getShape("circle");
        shape.draw();
    }
}
