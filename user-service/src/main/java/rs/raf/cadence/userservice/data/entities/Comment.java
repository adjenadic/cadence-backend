package rs.raf.cadence.userservice.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "commenter_id", nullable = false)
    private User commenter;

    private Long likes;

    private boolean edited;

    public Comment(String content, User user, User commenter) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        this.user = user;
        this.commenter = commenter;
        this.likes = 0L;
        this.edited = false;
    }
}
