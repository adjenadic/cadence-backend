package rs.raf.cadence.userservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.dtos.*;

import java.util.List;

/**
 * Service interface for managing User entities.
 * Extends UserDetailsService to provide Spring Security integration.
 * Provides business logic operations for user management.
 */
@Service
public interface UserService extends UserDetailsService {
    /**
     * Retrieves all users in the system.
     *
     * @return a list of all user DTOs
     */
    List<ResponseUserDto> findAllUsers();

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the user DTO with the specified ID
     */
    ResponseUserDto findUserById(Long id);

    /**
     * Retrieves a user by their email address.
     *
     * @param email the email address of the user
     * @return the user DTO with the specified email
     */
    ResponseUserDto findUserByEmail(String email);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user
     * @return the user DTO with the specified username
     */
    ResponseUserDto findUserByUsername(String username);

    /**
     * Verifies a user's email address using a verification token.
     *
     * @param verificationToken the verification token sent to the user's email
     * @return true if the email was successfully verified, false otherwise
     */
    boolean verifyEmail(String verificationToken);

    /**
     * Creates a new user account.
     *
     * @param requestCreateUserDto the data transfer object containing user creation information
     * @return the created user DTO
     */
    ResponseUserDto createUser(RequestCreateUserDto requestCreateUserDto);

    /**
     * Updates a user's email address.
     *
     * @param requestUpdateEmailDto the data transfer object containing new email information
     * @return the updated user DTO
     */
    ResponseUserDto updateEmail(RequestUpdateEmailDto requestUpdateEmailDto);

    /**
     * Updates a user's username.
     *
     * @param requestUpdateUsernameDto the data transfer object containing new username information
     * @return the updated user DTO
     */
    ResponseUserDto updateUsername(RequestUpdateUsernameDto requestUpdateUsernameDto);

    /**
     * Updates a user's password.
     *
     * @param requestUpdatePasswordDto the data transfer object containing new password information
     * @return the updated user DTO
     */
    ResponseUserDto updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto);

    /**
     * Updates a user's permissions.
     *
     * @param requestUpdatePermissionsDto the data transfer object containing new permissions information
     * @return the updated user DTO
     */
    ResponseUserDto updatePermissions(RequestUpdatePermissionsDto requestUpdatePermissionsDto);

    /**
     * Updates a user's pronouns.
     *
     * @param requestUpdatePronounsDto the data transfer object containing new pronouns information
     * @return the updated user DTO
     */
    ResponseUserDto updatePronouns(RequestUpdatePronounsDto requestUpdatePronounsDto);

    /**
     * Updates a user's about me section.
     *
     * @param requestUpdateAboutMeDto the data transfer object containing new about me information
     * @return the updated user DTO
     */
    ResponseUserDto updateAboutMe(RequestUpdateAboutMeDto requestUpdateAboutMeDto);

    /**
     * Updates a user's profile picture.
     *
     * @param requestUpdateProfilePictureDto the data transfer object containing new profile picture information
     * @return the updated user DTO
     */
    ResponseUserDto updateProfilePicture(RequestUpdateProfilePictureDto requestUpdateProfilePictureDto);

    /**
     * Deletes a user by their unique identifier.
     *
     * @param id the unique identifier of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    boolean deleteUserById(Long id);

    /**
     * Deletes a user by their email address.
     *
     * @param email the email address of the user to delete
     * @return true if the user was successfully deleted, false otherwise
     */
    boolean deleteUserByEmail(String email);
}
