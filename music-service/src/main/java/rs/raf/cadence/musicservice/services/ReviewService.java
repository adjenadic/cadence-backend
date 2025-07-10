package rs.raf.cadence.musicservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rs.raf.cadence.musicservice.clients.UserServiceClient;
import rs.raf.cadence.musicservice.data.dtos.CreateReviewDto;
import rs.raf.cadence.musicservice.data.dtos.ReviewDto;
import rs.raf.cadence.musicservice.data.dtos.UserDetailsDto;
import rs.raf.cadence.musicservice.data.entities.Review;
import rs.raf.cadence.musicservice.mappers.ReviewMapper;
import rs.raf.cadence.musicservice.repositories.AlbumRepository;
import rs.raf.cadence.musicservice.repositories.ReviewRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final AlbumRepository albumRepository;
    private final ReviewMapper reviewMapper;
    private final UserServiceClient userServiceClient;

    public List<ReviewDto> getReviewsByAlbum(String albumId) {
        List<Review> reviews = reviewRepository.findByAlbumIdOrderByTimestampDesc(albumId);
        return enrichReviewsWithUserDetails(reviews);
    }

    public List<ReviewDto> getReviewsByUser(Long userId) {
        List<Review> reviews = reviewRepository.findByUserIdOrderByTimestampDesc(userId)
                .stream()
                .limit(10)
                .collect(Collectors.toList());
        return enrichReviewsWithUserDetails(reviews);
    }

    public ReviewDto createOrUpdateReview(CreateReviewDto createReviewDto) {
        Optional<Review> existingReview = reviewRepository.findByAlbumIdAndUserId(
                createReviewDto.getAlbumId(), createReviewDto.getUserId());

        Review review;
        if (existingReview.isPresent()) {
            review = existingReview.get();
            review.setEdited(true);
        } else {
            review = new Review();
            review.setAlbumId(createReviewDto.getAlbumId());
            review.setUserId(createReviewDto.getUserId());
            review.setEdited(false);
        }

        review.setContent(createReviewDto.getContent());
        review.setRating(createReviewDto.getRating());
        review.setTimestamp(System.currentTimeMillis());

        review = reviewRepository.save(review);
        updateAlbumScore(createReviewDto.getAlbumId());

        List<ReviewDto> enrichedReviews = enrichReviewsWithUserDetails(List.of(review));
        return enrichedReviews.get(0);
    }

    public boolean deleteReview(String reviewId, Long userId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent() && review.get().getUserId().equals(userId)) {
            String albumId = review.get().getAlbumId();
            reviewRepository.deleteById(reviewId);
            updateAlbumScore(albumId);
            return true;
        }
        return false;
    }

    private void updateAlbumScore(String albumId) {
        List<Review> reviews = reviewRepository.findByAlbumIdOrderByTimestampDesc(albumId);

        if (reviews.isEmpty()) {
            albumRepository.findById(albumId).ifPresent(album -> {
                album.setIntScore(null);
                album.setIntScoreVotes(0L);
                albumRepository.save(album);
            });
        } else {
            double averageScore = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            albumRepository.findById(albumId).ifPresent(album -> {
                album.setIntScore(averageScore);
                album.setIntScoreVotes((long) reviews.size());
                albumRepository.save(album);
            });
        }
    }

    private List<ReviewDto> enrichReviewsWithUserDetails(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> userIds = reviews.stream()
                .map(Review::getUserId)
                .distinct()
                .collect(Collectors.toList());

        Map<Long, UserDetailsDto> userDetailsMap = userServiceClient.getUserDetailsBatch(userIds);

        return reviews.stream()
                .map(review -> {
                    UserDetailsDto userDetails = userDetailsMap.get(review.getUserId());
                    return reviewMapper.reviewToReviewDto(review, userDetails);
                })
                .collect(Collectors.toList());
    }
}