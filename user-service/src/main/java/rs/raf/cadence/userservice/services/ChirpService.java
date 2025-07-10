package rs.raf.cadence.userservice.services;

import rs.raf.cadence.userservice.data.dtos.RequestCreateChirpDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateChirpDto;
import rs.raf.cadence.userservice.data.dtos.ResponseChirpDto;

import java.util.List;

/**
 * Service interface for managing Chirp entities.
 * Provides business logic operations for chirp management.
 */
public interface ChirpService {
    /**
     * Retrieves all chirps for a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of chirp DTOs belonging to the specified user
     */
    List<ResponseChirpDto> findChirpsByUserId(Long userId);

    /**
     * Retrieves a chirp by its unique identifier.
     *
     * @param id the unique identifier of the chirp
     * @return the chirp DTO with the specified ID
     */
    ResponseChirpDto findChirpById(Long id);

    /**
     * Creates a new chirp.
     *
     * @param dto the data transfer object containing chirp creation information
     * @return the created chirp DTO
     */
    ResponseChirpDto createChirp(RequestCreateChirpDto dto);

    /**
     * Updates an existing chirp.
     *
     * @param dto the data transfer object containing chirp update information
     * @return the updated chirp DTO
     */
    ResponseChirpDto updateChirp(RequestUpdateChirpDto dto);

    /**
     * Adds a like to a chirp.
     *
     * @param id the unique identifier of the chirp to like
     * @return the updated chirp DTO with incremented like count
     */
    ResponseChirpDto likeChirp(Long id);

    /**
     * Removes a like from a chirp.
     *
     * @param id the unique identifier of the chirp to unlike
     * @return the updated chirp DTO with decremented like count
     */
    ResponseChirpDto unlikeChirp(Long id);

    /**
     * Deletes a chirp by its unique identifier.
     *
     * @param id the unique identifier of the chirp to delete
     * @return true if the chirp was successfully deleted, false otherwise
     */
    boolean deleteChirpById(Long id);
}
