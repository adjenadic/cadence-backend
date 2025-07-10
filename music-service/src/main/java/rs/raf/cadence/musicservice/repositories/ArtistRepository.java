package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.musicservice.data.entities.Artist;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Artist entity.
 * Extends MongoRepository to provide CRUD operations for Artist entities stored in MongoDB.
 */
@Repository
public interface ArtistRepository extends MongoRepository<Artist, String> {
    /**
     * Searches for artists by name, ignoring case.
     *
     * @param artistName the artist name to search for
     * @return a list of artists matching the search criteria
     */
    List<Artist> findByStrArtistContainingIgnoreCase(String artistName);

    /**
     * Retrieves an artist by their MongoDB ID or legacy ID.
     *
     * @param id       the MongoDB string ID of the artist
     * @param idArtist the legacy numeric ID of the artist
     * @return an Optional containing the found artist, or an empty Optional if no artist was found
     */
    Optional<Artist> findFirstByIdOrIdArtist(String id, Long idArtist);

    /**
     * Retrieves a random sample of artists that have thumbnails.
     *
     * @param count the number of random artists to retrieve
     * @return a list of randomly selected artists with thumbnails
     */
    @Aggregation(pipeline = {
            "{ $match: { 'strArtistThumb': { $nin: [null, '0', ''] } } }",
            "{ $sample: { size: ?0 } }"
    })
    List<Artist> findRandomArtistsWithThumbnails(int count);
}
