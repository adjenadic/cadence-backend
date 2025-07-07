package rs.raf.cadence.musicservice.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.musicservice.data.dtos.*;
import rs.raf.cadence.musicservice.data.entities.Album;
import rs.raf.cadence.musicservice.data.entities.Artist;
import rs.raf.cadence.musicservice.mappers.MusicCatalogMapper;
import rs.raf.cadence.musicservice.services.MusicCatalogService;
import rs.raf.cadence.musicservice.services.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/music")
@Slf4j
public class MusicCatalogController {
    private final MusicCatalogService musicCatalogService;
    private final MusicCatalogMapper musicCatalogMapper;
    private final ReviewService reviewService;

    @GetMapping("/search")
    public ResponseEntity<SearchResultsDto> search(@RequestParam String q) {
        SearchResultsDto results = musicCatalogService.searchAll(q);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/albums/{id}")
    public ResponseEntity<AlbumSummaryDto> getAlbumById(@PathVariable String id) {
        return musicCatalogService.getAlbumById(id)
                .map(musicCatalogMapper::albumToAlbumSummaryDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/albums/random")
    public ResponseEntity<List<AlbumSummaryDto>> getRandomAlbums(
            @RequestParam(defaultValue = "8") int count) {
        List<Album> albums = musicCatalogService.getRandomAlbums(count);
        List<AlbumSummaryDto> dtos = albums.stream()
                .map(musicCatalogMapper::albumToAlbumSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/albums/newest")
    public ResponseEntity<List<AlbumSummaryDto>> getNewestAlbums(
            @RequestParam(defaultValue = "10") int count) {
        List<Album> albums = musicCatalogService.getNewestAlbums(count);
        List<AlbumSummaryDto> dtos = albums.stream()
                .map(musicCatalogMapper::albumToAlbumSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/albums/top-selling")
    public ResponseEntity<List<AlbumSummaryDto>> getTopSellingAlbums(
            @RequestParam(defaultValue = "10") int count) {
        List<Album> albums = musicCatalogService.getTopSellingAlbums(count);
        List<AlbumSummaryDto> dtos = albums.stream()
                .map(musicCatalogMapper::albumToAlbumSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/albums/artist/{idArtist}")
    public ResponseEntity<List<AlbumSummaryDto>> getAlbumsByArtist(@PathVariable Long idArtist) {
        List<Album> albums = musicCatalogService.getAlbumsByArtist(idArtist);
        List<AlbumSummaryDto> dtos = albums.stream()
                .map(musicCatalogMapper::albumToAlbumSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/artists/{id}")
    public ResponseEntity<ArtistSummaryDto> getArtistById(@PathVariable String id) {
        return musicCatalogService.getArtistById(id)
                .map(musicCatalogMapper::artistToArtistSummaryDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/artists/random")
    public ResponseEntity<List<ArtistSummaryDto>> getRandomArtists(
            @RequestParam(defaultValue = "6") int count) {
        List<Artist> artists = musicCatalogService.getRandomArtists(count);
        List<ArtistSummaryDto> dtos = artists.stream()
                .map(musicCatalogMapper::artistToArtistSummaryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/albums/{id}/reviews")
    public ResponseEntity<List<ReviewDto>> getAlbumReviews(@PathVariable String id) {
        List<ReviewDto> reviews = reviewService.getReviewsByAlbum(id);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/users/{userId}/reviews")
    public ResponseEntity<List<ReviewDto>> getUserReviews(@PathVariable Long userId) {
        List<ReviewDto> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/reviews")
    public ResponseEntity<ReviewDto> createReview(@RequestBody CreateReviewDto createReviewDto) {
        ReviewDto review = reviewService.createOrUpdateReview(createReviewDto);
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable String reviewId, @RequestParam Long userId) {
        boolean deleted = reviewService.deleteReview(reviewId, userId);
        return ResponseEntity.ok(deleted);
    }
}
