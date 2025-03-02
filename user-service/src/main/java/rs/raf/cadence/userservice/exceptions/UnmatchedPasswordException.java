package rs.raf.cadence.userservice.exceptions;

public class UnmatchedPasswordException extends RuntimeException {
    public UnmatchedPasswordException() {
        super("The entered passwords do not match.");
    }
}
