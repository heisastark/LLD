package DesignPatterns;

interface Shape{
    public void draw();
    public int getArea();
}

class Circle implements Shape{
    int radius;
    public Circle(int radius){
        this.radius = radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing Circle!");
    }

    @Override
    public int getArea() {
        return 0;
    }
}

class Rectangle implements Shape{
    int length;
    int width;
    public Rectangle(int length, int width){
        this.length = length;
        this.width = width;
    }

    public int getLength(){
        return length;
    }

    public int getWidth(){
        return width;
    }

    @Override
    public void draw(){
        System.out.println("Drawing Rectangle!");
    }

    @Override
    public int getArea() {
        return getLength() * getWidth();
    }
}

class Square extends Rectangle{
    public Square(int length) {
        super(length, length);
    }

    @Override
    public void draw(){
        System.out.println("Drawing Square!");
    }
}

class ShapeFactory{
    public Shape getShape(String shape){
        switch (shape){
            case "circle": return new Circle(0);
            case "rectangle": return new Rectangle(0,0);
            case "square": return new Square(0);
        }
        return null;
    }
}

public class Factory {
    public static void main(String args[]){
        ShapeFactory shapeFactory = new ShapeFactory();
        Shape shape = shapeFactory.getShape("rectangle");
        shape.draw();
    }
}
