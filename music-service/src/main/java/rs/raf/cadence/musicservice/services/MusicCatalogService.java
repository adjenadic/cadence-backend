package rs.raf.cadence.musicservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.raf.cadence.musicservice.data.entities.Album;
import rs.raf.cadence.musicservice.data.entities.Artist;
import rs.raf.cadence.musicservice.repositories.AlbumRepository;
import rs.raf.cadence.musicservice.repositories.ArtistRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicCatalogService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public Optional<Album> getAlbumById(String id) {
        log.info("Fetching album by id: {}", id);

        Optional<Album> album = albumRepository.findById(id);

        if (album.isPresent()) {
            log.info("Found album: {}", album.get().getStrAlbum());
        } else {
            log.warn("Album not found with id: {}", id);
        }

        return album;
    }

    public List<Album> getRandomAlbums(int count) {
        log.info("Fetching {} random albums", count);
        long startTime = System.currentTimeMillis();

        List<Album> result = albumRepository.findRandomAlbumsWithThumbnails(count);

        long totalTime = System.currentTimeMillis() - startTime;
        log.info("Total operation took {} ms, returning {} random albums", totalTime, result.size());
        return result;
    }

    public List<Album> getNewestAlbums(int count) {
        log.info("Fetching {} newest albums", count);
        long startTime = System.currentTimeMillis();

        List<Album> result = albumRepository.findNewestAlbums(count);

        long totalTime = System.currentTimeMillis() - startTime;
        log.info("Total operation took {} ms, returning {} newest albums", totalTime, result.size());
        return result;
    }

    public List<Album> getTopSellingAlbums(int count) {
        log.info("Fetching {} top selling albums", count);
        long startTime = System.currentTimeMillis();

        List<Album> result = albumRepository.findTopSellingAlbums(count);

        long totalTime = System.currentTimeMillis() - startTime;
        log.info("Total operation took {} ms, returning {} top selling albums", totalTime, result.size());
        return result;
    }

    public List<Album> getAlbumsByArtist(Long idArtist) {
        log.info("Fetching albums for artist: {}", idArtist);

        List<Album> albums = albumRepository.findAlbumByIdArtist(idArtist);

        log.info("Found {} albums for artist: {}", albums.size(), idArtist);
        return albums;
    }

    public Optional<Artist> getArtistById(String id) {
        log.info("Fetching artist by id: {}", id);

        Long idArtistValue = null;
        try {
            idArtistValue = Long.parseLong(id);
        } catch (NumberFormatException e) {
            log.info("{} is not a number, searching only by id", id);
        }

        Optional<Artist> artist = artistRepository.findFirstByIdOrIdArtist(id, idArtistValue);

        if (artist.isPresent()) {
            log.info("Found artist with idArtist: {}", artist.get().getIdArtist());
        } else {
            log.warn("Artist not found with id: {}", id);
        }

        return artist;
    }

    public List<Artist> getRandomArtists(int count) {
        log.info("Fetching {} random artists", count);
        long startTime = System.currentTimeMillis();

        List<Artist> result = artistRepository.findRandomArtistsWithThumbnails(count);

        long totalTime = System.currentTimeMillis() - startTime;
        log.info("Total operation took {} ms, returning {} random artists", totalTime, result.size());
        return result;
    }
}
