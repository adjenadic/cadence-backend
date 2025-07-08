package rs.raf.cadence.userservice.data.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private boolean emailVerified = false;
    private String verificationToken;
    private Long verificationTokenExpiry;
    private String username;
    private String password;
    private String pronouns;
    @Column(length = 512)
    private String aboutMe;
    @Column(columnDefinition = "TEXT")
    private String profilePicture;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Chirp> receivedChirps = new HashSet<>();

    @OneToMany(mappedBy = "chirper", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Chirp> createdChirps = new HashSet<>();

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }
}
