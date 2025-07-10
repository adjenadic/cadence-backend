package rs.raf.cadence.userservice.services;

import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.entities.User;

/**
 * Service interface for dispatching email messages.
 * Provides methods for sending various types of user-related email notifications.
 */
@Service
public interface MessageDispatchService {
    /**
     * Sends an account verification email to the user.
     *
     * @param user the user to send the verification email to
     */
    void sendAccountVerificationEmail(User user);

    /**
     * Sends an email change confirmation email to the user.
     *
     * @param user the user to send the email change confirmation to
     */
    void sendEmailChangeEmail(User user);

    /**
     * Sends a password change confirmation email to the user.
     *
     * @param user the user to send the password change confirmation to
     */
    void sendPasswordChangeEmail(User user);
}
