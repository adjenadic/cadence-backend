package rs.raf.cadence.musicservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.musicservice.data.dtos.ReviewDto;
import rs.raf.cadence.musicservice.data.dtos.UserDetailsDto;
import rs.raf.cadence.musicservice.data.entities.Review;

@Component
public class ReviewMapper {
    public ReviewDto reviewToReviewDto(Review review, UserDetailsDto userDetails) {
        return new ReviewDto(
                review.getId(),
                review.getAlbumId(),
                review.getUserId(),
                userDetails != null ? userDetails.getUsername() : "User " + review.getUserId(),
                userDetails != null ? userDetails.getProfilePicture() : null,
                review.getContent(),
                review.getRating(),
                review.getTimestamp(),
                review.isEdited()
        );
    }
}
