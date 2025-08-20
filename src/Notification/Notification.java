package Notification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Notification Request
class NotificationRequest {
    String message;
    LocalDateTime scheduleTime;

    public NotificationRequest(String message, LocalDateTime scheduleTime) {
        this.message = message;
        this.scheduleTime = scheduleTime;
    }
}

// Observer Interface
interface NotificationObserver {
    void notify(NotificationRequest request);
}

// Concrete Observers
class EmailSender implements NotificationObserver {
    String emailId;
    public EmailSender(String emailId){
        this.emailId = emailId;
    }

    @Override
    public void notify(NotificationRequest request) {
        System.out.println("[EMAIL] Sending to " + emailId + " : " + request.message);
    }
}

class SmsSender implements NotificationObserver {
    String phoneNo;
    public SmsSender(String phoneNo){
        this.phoneNo = phoneNo;
    }

    @Override
    public void notify(NotificationRequest request) {
        System.out.println("[SMS] Sending to " + phoneNo + " : " + request.message);
    }
}

class PushSender implements NotificationObserver {
    String deviceId;
    public PushSender(String deviceId){
        this.deviceId = deviceId;
    }

    @Override
    public void notify(NotificationRequest request) {
        System.out.println("[PUSH] Sending to " + deviceId + " : " + request.message);
    }
}

// Subject
class NotificationService {
    private List<NotificationObserver> observers = new ArrayList<>();

    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(NotificationRequest request) {
        for (NotificationObserver observer : observers) {
            observer.notify(request);
        }
    }

    public void createNotification(NotificationRequest request) {
        if (request.scheduleTime.isAfter(LocalDateTime.now())) {
            System.out.println("Scheduling notification for " + request.scheduleTime);
            // In real impl: add to scheduler/queue
        } else {
            notifyObservers(request);
        }
    }
}

// Main class
public class Notification {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        // Attach observers
        service.attach(new EmailSender("dost@gmail.com"));
        service.attach(new SmsSender("+91-12345678"));
        service.attach(new PushSender("abc56yt551"));

        // Fire notification
        NotificationRequest req = new NotificationRequest(
                "Thank you! Your order is confirmed.",
                LocalDateTime.now()
        );

        service.createNotification(req);
    }
}