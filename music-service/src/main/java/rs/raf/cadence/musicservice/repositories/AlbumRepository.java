package rs.raf.cadence.musicservice.repositories;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.musicservice.data.entities.Album;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for the Album entity.
 * Extends MongoRepository to provide CRUD operations for Album entities stored in MongoDB.
 */
@Repository
public interface AlbumRepository extends MongoRepository<Album, String> {
    /**
     * Searches for albums by album name or artist name, ignoring case.
     *
     * @param albumName  the album name to search for
     * @param artistName the artist name to search for
     * @return a list of albums matching the search criteria
     */
    List<Album> findByStrAlbumContainingIgnoreCaseOrStrArtistContainingIgnoreCase(String albumName, String artistName);

    /**
     * Retrieves an album by its MongoDB ID or legacy ID.
     *
     * @param id      the MongoDB string ID of the album
     * @param idAlbum the legacy numeric ID of the album
     * @return an Optional containing the found album, or an empty Optional if no album was found
     */
    Optional<Album> findFirstByIdOrIdAlbum(String id, Long idAlbum);

    /**
     * Retrieves a random sample of albums that have thumbnails.
     *
     * @param count the number of random albums to retrieve
     * @return a list of randomly selected albums with thumbnails
     */
    @Aggregation(pipeline = {
            "{ $match: { 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sample: { size: ?0 } }"
    })
    List<Album> findRandomAlbumsWithThumbnails(int count);

    /**
     * Retrieves the newest albums that have thumbnails, sorted by release year.
     *
     * @param count the number of newest albums to retrieve
     * @return a list of the newest albums with thumbnails, ordered by release year descending
     */
    @Aggregation(pipeline = {
            "{ $match: { 'intYearReleased': { $ne: null }, 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sort: { 'intYearReleased': -1 } }",
            "{ $limit: ?0 }"
    })
    List<Album> findNewestAlbums(int count);

    /**
     * Retrieves the top-selling albums that have thumbnails, sorted by sales.
     *
     * @param count the number of top-selling albums to retrieve
     * @return a list of the top-selling albums with thumbnails, ordered by sales descending
     */
    @Aggregation(pipeline = {
            "{ $match: { 'intSales': { $ne: null }, 'strAlbumThumb': { $nin: [null, '0', ''] } } }",
            "{ $sort: { 'intSales': -1 } }",
            "{ $limit: ?0 }"
    })
    List<Album> findTopSellingAlbums(int count);

    /**
     * Retrieves all albums by a specific artist.
     *
     * @param idArtist the unique identifier of the artist
     * @return a list of albums by the specified artist
     */
    List<Album> findAlbumByIdArtist(Long idArtist);
}
