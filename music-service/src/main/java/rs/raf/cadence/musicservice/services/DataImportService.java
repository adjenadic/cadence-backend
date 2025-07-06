package rs.raf.cadence.musicservice.services;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import rs.raf.cadence.musicservice.data.entities.Album;
import rs.raf.cadence.musicservice.data.entities.Artist;
import rs.raf.cadence.musicservice.repositories.AlbumRepository;
import rs.raf.cadence.musicservice.repositories.ArtistRepository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
public class DataImportService {
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    public DataImportService(ArtistRepository artistRepository, AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
    }

    public void importCsvData() {
        if (artistRepository.count() == 0) {
            log.info("Artist data not found, importing from CSV file...");
            importArtists();
        } else {
            log.info("Artist data already exists, skipping artist import");
        }

        if (albumRepository.count() == 0) {
            log.info("Album data not found, importing from CSV file...");
            importAlbums();
        } else {
            log.info("Album data already exists, skipping album import");
        }
    }

    private void importArtists() {
        try {
            log.info("Loading artists from CSV file...");
            List<Artist> artists = new CsvToBeanBuilder<Artist>(new InputStreamReader(new ClassPathResource("data/artists.csv").getInputStream()))
                    .withType(Artist.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            log.info("Parsed {} artists from CSV", artists.size());

            artistRepository.saveAll(artists);
            log.info("Finished loading artists.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void importAlbums() {
        try {
            log.info("Loading albums from CSV file...");
            List<Album> albums = new CsvToBeanBuilder<Album>(new InputStreamReader(new ClassPathResource("data/albums.csv").getInputStream()))
                    .withType(Album.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            log.info("Parsed {} albums from CSV", albums.size());

            albumRepository.saveAll(albums);
            log.info("Finished loading albums.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
