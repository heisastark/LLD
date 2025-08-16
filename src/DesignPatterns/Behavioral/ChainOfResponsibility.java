package DesignPatterns.Behavioral;

interface LogHandler{
    void handleLog(Log log);
//    void setNextLogHandler(LogHandler logHandler);
}

class Log{
    private String message;
    private Severity severity;

    public Log(String message, Severity severity){
        this.message = message;
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public Severity getSeverity() {
        return severity;
    }
}

enum Severity{
    INFO,
    WARNING,
    ERROR
}

class InfoHandler implements LogHandler{
    private LogHandler nextLogHandler;

    private void setNextLogHandler(LogHandler nextLogHandler) {
        this.nextLogHandler = nextLogHandler;
    }

    @Override
    public void handleLog(Log log) {
        if(log.getSeverity() == Severity.INFO){
            System.out.println("INFO: " + log.getMessage());
        }
        if(log.getSeverity() == Severity.ERROR){
            setNextLogHandler(new ErrorHandler());
            nextLogHandler.handleLog(log);
        }
        if(log.getSeverity() == Severity.WARNING){
            setNextLogHandler(new WarningHandler());
            nextLogHandler.handleLog(log);
        }
    }
}

class WarningHandler implements LogHandler{
    private LogHandler nextLogHandler;

    private void setNextLogHandler(LogHandler nextLogHandler) {
        this.nextLogHandler = nextLogHandler;
    }

    @Override
    public void handleLog(Log log) {
        if(log.getSeverity() == Severity.WARNING){
            System.out.println("WARN: " + log.getMessage());
        }
        if(log.getSeverity() == Severity.INFO){
            setNextLogHandler(new InfoHandler());
            nextLogHandler.handleLog(log);
        }
        if(log.getSeverity() == Severity.ERROR){
            setNextLogHandler(new ErrorHandler());
            nextLogHandler.handleLog(log);
        }
    }
}

class ErrorHandler implements LogHandler{
    private LogHandler nextLogHandler;

    private void setNextLogHandler(LogHandler nextLogHandler) {
        this.nextLogHandler = nextLogHandler;
    }

    @Override
    public void handleLog(Log log) {
        if(log.getSeverity() == Severity.ERROR){
            System.out.println("ERROR: " + log.getMessage());
        }
        if(log.getSeverity() == Severity.INFO){
            setNextLogHandler(new InfoHandler());
            nextLogHandler.handleLog(log);
        }
        if(log.getSeverity() == Severity.WARNING){
            setNextLogHandler(new WarningHandler());
            nextLogHandler.handleLog(log);
        }
    }
}

public class ChainOfResponsibility {
    public static void main(String[] args){
        LogHandler logHandler = new InfoHandler();

        Log log = new Log("Damn! Something broke bro :(", Severity.ERROR);
        logHandler.handleLog(log);

        Log someOtherLog = new Log("Uh oh! Threshold reaching now!", Severity.WARNING);
        logHandler.handleLog(someOtherLog);
    }
}
