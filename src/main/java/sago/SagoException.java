package sago;

/**
 * Represents an application-specific exception thrown when user input is invalid
 * or when an operation cannot be completed.
 */
public class SagoException extends Exception{
    public SagoException(String message) {
        super(message);
    }
}
