package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.userservice.data.entities.User;

import java.util.Optional;

/**
 * Repository interface for the User entity.
 * Extends JpaRepository to provide CRUD operations for User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Retrieves an optional user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return an Optional containing the found user, or an empty Optional if no user was found with the provided id
     */
    Optional<User> findUserById(Long id);

    /**
     * Retrieves an optional user by their email.
     *
     * @param email the email of the user
     * @return an Optional containing the found user, or an empty Optional if no user was found with the provided email
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Retrieves an optional user by their username.
     *
     * @param username the username of the user
     * @return an Optional containing the found user, or an empty Optional if no user was found with the provided username
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Deletes an entity by their unique identifier.
     *
     * @param id the unique identifier of the user
     */
    void deleteById(Long id);

    /**
     * Deletes an entity by their email.
     *
     * @param email the email of the user
     */
    void deleteByEmail(String email);
}
