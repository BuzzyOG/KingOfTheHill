package net.thelightmc.exceptions;

public class NoGameRunningException extends Exception {
    public NoGameRunningException() {
        super("No Game is being run,");
    }
    public NoGameRunningException(String s) {
        super(s);
    }
}
