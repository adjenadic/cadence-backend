package rs.raf.cadence.musicservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private String id;
    private String albumId;
    private Long userId;
    private String username;
    private String userProfilePicture;
    private String content;
    private Integer rating;
    private Long timestamp;
    private boolean edited;
}
