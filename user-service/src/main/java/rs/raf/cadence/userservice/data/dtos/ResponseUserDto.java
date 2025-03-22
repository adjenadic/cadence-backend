package rs.raf.cadence.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.cadence.userservice.data.enums.PermissionType;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String email;
    private boolean emailVerified;
    private String username;
    private String pronouns;
    private String aboutMe;
    private String profilePicture;
    private Set<PermissionType> permissions = new HashSet<>();
}
