package rs.raf.cadence.userservice.exceptions;

public class VerificationCodeExpiredException extends RuntimeException {
    public VerificationCodeExpiredException(String email) {
        super("Verification code for email " + email + " has expired. Please retry signing up later.");
    }
}
