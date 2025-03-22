package rs.raf.cadence.userservice.exceptions;

public class VerificationCodeNotFoundException extends RuntimeException {
    public VerificationCodeNotFoundException(String email) {
        super("Verification code not found for user " + email + ".");
    }
}
