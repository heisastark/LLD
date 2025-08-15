package DesignPatterns.Behavioral;

interface DocumentState{
    void publish(Document document);
}

class DraftState implements DocumentState{
    @Override
    public void publish(Document document) {
        System.out.println("Document Draft Saved, moving to Review State");
        document.setState(new ReviewState());
        //document.publish();
    }
}

class ReviewState implements DocumentState{
    @Override
    public void publish(Document document) {
        System.out.println("Document Review completed, moving to Publish State");
        document.setState(new PublishState());
        //document.publish();
    }
}

class PublishState implements DocumentState{
    @Override
    public void publish(Document document) {
        System.out.println("Document Already Published!");
    }
}

class Document{
    DocumentState state;
    public Document(){
        this.state = new DraftState();
    }
    public void setState(DocumentState documentState){
        this.state = documentState;
    }
    public void publish(){
        state.publish(this);
    }
}

public class State {
    public static void main(String[] args){
        Document document = new Document();
        document.publish();
    }
}
