package rs.raf.cadence.userservice.exceptions;

public class InvalidVerificationCodeException extends RuntimeException {
    public InvalidVerificationCodeException() {
        super("Invalid verification code. Please try again.");
    }
}
