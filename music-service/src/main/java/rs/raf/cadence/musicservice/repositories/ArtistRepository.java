package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rs.raf.cadence.musicservice.data.entities.Artist;

public interface ArtistRepository extends MongoRepository<Artist, String> {

}
