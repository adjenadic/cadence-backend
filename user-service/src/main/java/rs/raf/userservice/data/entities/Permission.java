package rs.raf.userservice.data.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.userservice.data.enums.PermissionType;

import java.util.List;

@Entity
@Table(name = "permissions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission {
    @ManyToMany(mappedBy = "permissions")
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private PermissionType permissionType;

    public Permission(PermissionType permissionType) {
        this.permissionType = permissionType;
    }
}
