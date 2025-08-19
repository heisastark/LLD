package MessageQueue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Subscriber {
    String name;
    ArrayList<Topic> topics;
    ArrayList<Message> messages;

    public Subscriber(String name) {
        this.name = name;
        topics = new ArrayList<>();
        messages = new ArrayList<>();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
        topic.addSubcriber(this);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
        topic.removeSubscriber(this);
    }

    public void notifyMessage(Message message) {
        messages.add(message);
        System.out.println("Subscriber " + name + " received message: " + message.body);
    }
}

enum MessageStatus {
    INITIATED, DELIVERING, DELIVERED, FAILED
}

class Message {
    String title;
    String body;
    MessageStatus status;

    public Message(String title, String body) {
        this.title = title;
        this.body = body;
        this.status = MessageStatus.INITIATED;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }
}

class Publisher {
    String name;
    ArrayList<Topic> topics;

    public Publisher(String name) {
        this.name = name;
        this.topics = new ArrayList<>();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public void removeTopic(Topic topic) {
        topics.remove(topic);
    }

    public void publish(Message message) {
        for (Topic topic : topics) {
            boolean isAdded = topic.addMessage(message);
            if (isAdded) {
                System.out.println("Message successfully added to the Topic : " + topic.getName());
            } else {
                System.out.println("Message unable to be added to the Topic : " + topic.getName() + " as it exceeds the queue capacity.");
            }
        }
    }
}

class Topic {
    String name;
    ArrayList<Subscriber> subscribers;
    BlockingQueue<Message> messages;
    int capacity;
    int pollingIntervalInSeconds;

    public Topic(String name, int capacity, int pollingIntervalInSeconds) {
        this.name = name;
        messages = new ArrayBlockingQueue<>(capacity);
        subscribers = new ArrayList<>();
        this.capacity = capacity;
        this.pollingIntervalInSeconds = pollingIntervalInSeconds;
    }

    public String getName() {
        return name;
    }

    public void addSubcriber(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public boolean addMessage(Message message) {
        return messages.offer(message);
    }

    public void startMessageDelivery() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Message message = messages.take(); // blocks until available
                    deliverMessage(message);
                    Thread.sleep(pollingIntervalInSeconds * 1000L);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.start();
    }

    public void deliverMessage(Message message) {
        message.setStatus(MessageStatus.DELIVERING);
        for (Subscriber subscriber : subscribers) {
            subscriber.notifyMessage(message);
        }
        message.setStatus(MessageStatus.DELIVERED);
    }
}

public class MessageQueue {
    HashMap<String, Subscriber> subscribers;
    HashMap<String, Publisher> publishers;
    HashMap<String, Topic> topics;

    private static volatile MessageQueue instance;

    private MessageQueue() {
        subscribers = new HashMap<>();
        publishers = new HashMap<>();
        topics = new HashMap<>();
    }

    public static MessageQueue getInstance() {
        if (instance == null) {
            synchronized (MessageQueue.class) {
                if (instance == null) {
                    instance = new MessageQueue();
                }
            }
        }
        return instance;
    }

    public void addSubscriber(String name) {
        subscribers.put(name, new Subscriber(name));
    }

    public void addPublisher(String name) {
        publishers.put(name, new Publisher(name));
    }

    public void addTopic(String name, int capacity, int pollingIntervalInSeconds) {
        topics.put(name, new Topic(name, capacity, pollingIntervalInSeconds));
    }
}

class Main {
    public static void main(String[] args) throws InterruptedException {
        MessageQueue mq = MessageQueue.getInstance();

        mq.addTopic("Sports", 10, 1);
        mq.addSubscriber("Alice");
        mq.addPublisher("ESPN");

        Topic sports = mq.topics.get("Sports");
        Subscriber alice = mq.subscribers.get("Alice");
        Publisher espn = mq.publishers.get("ESPN");

        alice.addTopic(sports);
        espn.addTopic(sports);

        sports.startMessageDelivery();

        espn.publish(new Message("Cricket", "India won the match!"));
        espn.publish(new Message("Football", "Messi scored a goal."));

        Thread.sleep(5000); // wait to see delivery
    }
}