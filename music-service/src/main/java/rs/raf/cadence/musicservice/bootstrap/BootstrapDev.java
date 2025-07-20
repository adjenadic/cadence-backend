package rs.raf.cadence.musicservice.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.raf.cadence.musicservice.data.entities.Review;
import rs.raf.cadence.musicservice.repositories.ReviewRepository;
import rs.raf.cadence.musicservice.services.DataImportService;

@Slf4j
@Component
@RequiredArgsConstructor
public class BootstrapDev implements CommandLineRunner {
    private final DataImportService dataImportService;
    private final ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {
        dataImportService.importCsvData();

        createSampleReviews();
    }

    private void createSampleReviews() {
        if (reviewRepository.count() > 0) {
            return;
        }

        Review review1 = new Review();
        review1.setAlbumId("2109569");
        review1.setUserId(1L);
        review1.setContent("Amazing album! The production quality is outstanding and every track flows perfectly.");
        review1.setRating(9);
        review1.setTimestamp(System.currentTimeMillis() - 86400000); // 1 day ago
        review1.setEdited(false);

        Review review2 = new Review();
        review2.setAlbumId("2109569");
        review2.setUserId(2L);
        review2.setContent("Good album overall, but some tracks feel repetitive. Still worth a listen.");
        review2.setRating(7);
        review2.setTimestamp(System.currentTimeMillis() - 43200000); // 12 hours ago
        review2.setEdited(false);

        reviewRepository.save(review1);
        reviewRepository.save(review2);

        log.info("Saved sample reviews with IDs: {} and {}", review1.getId(), review2.getId());
    }
}
