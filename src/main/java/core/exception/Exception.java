package core.exception;


public class Exception extends RuntimeException {

    private final ExceptionType type;

    public Exception(ExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    public Exception(ExceptionType type, String message, java.lang.Exception e) {
        super(message, e);
        this.type = type;
    }

    public ExceptionType getType() {
        return type;
    }
}
