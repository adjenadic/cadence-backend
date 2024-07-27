package rs.raf.userservice.exceptions;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String email) {
        super("Email is already taken: " + email);
    }
}
