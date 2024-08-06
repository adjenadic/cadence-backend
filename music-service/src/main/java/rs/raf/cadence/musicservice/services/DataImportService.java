package rs.raf.cadence.musicservice.services;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import rs.raf.cadence.musicservice.data.entities.Album;
import rs.raf.cadence.musicservice.data.entities.Artist;
import rs.raf.cadence.musicservice.repositories.AlbumRepository;
import rs.raf.cadence.musicservice.repositories.ArtistRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DataImportService {
    private static final Logger logger = LoggerFactory.getLogger(DataImportService.class);
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public DataImportService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public void importCsvData() {
        importArtists();
        importAlbums();
    }

    private void importArtists() {
        try {
            logger.info("Loading artists from CSV file...");
            List<Artist> artists = new CsvToBeanBuilder(new InputStreamReader(new ClassPathResource("data/artists.csv").getInputStream()))
                    .withType(Artist.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            artistRepository.saveAll(artists);
            logger.info("Finished loading artists.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importAlbums() {
        try {
            logger.info("Loading albums from CSV file...");
            List<Album> albums = new CsvToBeanBuilder(new InputStreamReader(new ClassPathResource("data/albums.csv").getInputStream()))
                    .withType(Album.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            albumRepository.saveAll(albums);
            logger.info("Finished loading albums.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
