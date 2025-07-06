package rs.raf.cadence.userservice.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ChirpNotFoundException extends UsernameNotFoundException {
    public ChirpNotFoundException(Long id) {
        super("Chirp with id " + id + " not found.");
    }
}
