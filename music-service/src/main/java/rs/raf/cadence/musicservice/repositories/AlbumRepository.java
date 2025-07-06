package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import rs.raf.cadence.musicservice.data.entities.Album;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends MongoRepository<Album, String> {
    List<Album> findByStrAlbumContainingIgnoreCaseOrStrArtistContainingIgnoreCase(String albumName, String artistName);

    Optional<Album> findFirstByIdOrIdAlbum(String id, Long idAlbum);

    @Aggregation(pipeline = {
            "{ $match: { 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sample: { size: ?0 } }"
    })
    List<Album> findRandomAlbumsWithThumbnails(int count);

    @Aggregation(pipeline = {
            "{ $match: { 'intYearReleased': { $ne: null }, 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sort: { 'intYearReleased': -1 } }",
            "{ $limit: ?0 }"
    })
    List<Album> findNewestAlbums(int count);

    @Aggregation(pipeline = {
            "{ $match: { 'intSales': { $ne: null }, 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sort: { 'intSales': -1 } }",
            "{ $limit: ?0 }"
    })
    List<Album> findTopSellingAlbums(int count);

    List<Album> findAlbumByIdArtist(Long idArtist);
}
