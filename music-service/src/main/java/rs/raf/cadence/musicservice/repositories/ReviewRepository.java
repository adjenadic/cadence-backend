package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.musicservice.data.entities.Review;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Review entity.
 * Extends MongoRepository to provide CRUD operations for Review entities stored in MongoDB.
 */
@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    /**
     * Retrieves all reviews for a specific album, ordered by timestamp descending.
     *
     * @param albumId the unique identifier of the album
     * @return a list of reviews for the specified album, ordered from newest to oldest
     */
    List<Review> findByAlbumIdOrderByTimestampDesc(String albumId);

    /**
     * Retrieves all reviews by a specific user, ordered by timestamp descending.
     *
     * @param userId the unique identifier of the user
     * @return a list of reviews by the specified user, ordered from newest to oldest
     */
    List<Review> findByUserIdOrderByTimestampDesc(Long userId);

    /**
     * Retrieves a review by album ID and user ID.
     *
     * @param albumId the unique identifier of the album
     * @param userId  the unique identifier of the user
     * @return an Optional containing the found review, or an empty Optional if no review was found
     */
    Optional<Review> findByAlbumIdAndUserId(String albumId, Long userId);

    /**
     * Counts the total number of reviews for a specific album.
     *
     * @param albumId the unique identifier of the album
     * @return the total count of reviews for the specified album
     */
    long countByAlbumId(String albumId);
}
