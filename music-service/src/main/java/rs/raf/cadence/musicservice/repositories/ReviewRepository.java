package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rs.raf.cadence.musicservice.data.entities.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByAlbumIdOrderByTimestampDesc(String albumId);

    List<Review> findByUserIdOrderByTimestampDesc(Long userId);

    Optional<Review> findByAlbumIdAndUserId(String albumId, Long userId);

    long countByAlbumId(String albumId);
}
