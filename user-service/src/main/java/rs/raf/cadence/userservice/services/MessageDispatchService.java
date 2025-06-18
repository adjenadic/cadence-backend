package rs.raf.cadence.userservice.services;

import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.entities.User;

@Service
public interface MessageDispatchService {
    void sendAccountVerificationEmail(User user);
    void sendEmailChangeEmail(User user);
    void sendPasswordChangeEmail(User user);
}
