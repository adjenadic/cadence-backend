package rs.raf.userservice.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.raf.userservice.data.enums.PermissionType;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {
    private Long id;
    private String email;
    private String username;
    private Set<PermissionType> permissions = new HashSet<>();
}
