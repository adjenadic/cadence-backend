package rs.raf.cadence.userservice.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CommentNotFoundException extends UsernameNotFoundException {
    public CommentNotFoundException(Long id) {
        super("Comment with id " + id + " not found.");
    }
}
