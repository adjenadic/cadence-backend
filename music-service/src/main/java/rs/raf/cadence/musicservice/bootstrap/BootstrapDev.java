package rs.raf.cadence.musicservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.raf.cadence.musicservice.repositories.AlbumRepository;
import rs.raf.cadence.musicservice.repositories.ArtistRepository;
import rs.raf.cadence.musicservice.services.DataImportService;

@Component
@RequiredArgsConstructor
public class BootstrapDev implements CommandLineRunner {
    private final DataImportService dataImportService;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;

    @Override
    public void run(String... args) {
        dataImportService.importCsvData();
    }
}
