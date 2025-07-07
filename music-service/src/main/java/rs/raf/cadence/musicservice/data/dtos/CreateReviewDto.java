package rs.raf.cadence.musicservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateReviewDto {
    private String albumId;
    private Long userId;
    private String content;
    private Integer rating;
}
