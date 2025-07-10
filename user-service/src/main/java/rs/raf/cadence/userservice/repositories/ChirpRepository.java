package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.userservice.data.entities.Chirp;

import java.util.List;

/**
 * Repository interface for the Chirp entity.
 * Extends JpaRepository to provide CRUD operations for Chirp entities.
 */
@Repository
public interface ChirpRepository extends JpaRepository<Chirp, Long> {
    /**
     * Retrieves a list of chirps by user ID, ordered by timestamp in descending order.
     *
     * @param userId the unique identifier of the user whose chirps to retrieve
     * @return a list of chirps belonging to the specified user, ordered from newest to oldest
     */
    List<Chirp> findChirpsByUserIdOrderByTimestampDesc(Long userId);
}
