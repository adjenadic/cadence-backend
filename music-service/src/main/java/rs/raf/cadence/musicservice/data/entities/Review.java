package rs.raf.cadence.musicservice.data.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private String id;
    @Indexed
    private String albumId;
    @Indexed
    private Long userId;
    private String content;
    private Integer rating; // 1-10
    private Long timestamp;
    private boolean edited;

    public Review(String id, Long userId, String content, Integer rating) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
    }
}
