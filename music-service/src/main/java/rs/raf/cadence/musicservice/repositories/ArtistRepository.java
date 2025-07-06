package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import rs.raf.cadence.musicservice.data.entities.Artist;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository extends MongoRepository<Artist, String> {
    List<Artist> findByStrArtistContainingIgnoreCase(String artistName);

    Optional<Artist> findFirstByIdOrIdArtist(String id, Long idArtist);

    @Aggregation(pipeline = {
            "{ $match: { 'strArtistThumb': { $nin: [null, '0', ''] } } }",
            "{ $sample: { size: ?0 } }"
    })
    List<Artist> findRandomArtistsWithThumbnails(int count);
}
