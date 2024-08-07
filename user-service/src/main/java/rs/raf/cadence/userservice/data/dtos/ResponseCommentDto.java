package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommentDto {
    private Long id;
    private String content;
    private Long timestamp;
    private Long userId;
    private Long commenterId;
    private Long likes;
    private boolean edited;
}
