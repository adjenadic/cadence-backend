package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import rs.raf.cadence.musicservice.data.entities.Album;

public interface AlbumRepository extends MongoRepository<Album, String> {

}
