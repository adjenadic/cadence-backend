package rs.raf.cadence.userservice.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "chirps")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chirp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private Long timestamp;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "chirper_id", nullable = false)
    private User chirper;

    private Long likes;

    private boolean edited;

    public Chirp(String content, User user, User chirper) {
        this.content = content;
        this.timestamp = System.currentTimeMillis();
        this.user = user;
        this.chirper = chirper;
        this.likes = 0L;
        this.edited = false;
    }
}
