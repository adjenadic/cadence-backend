package rs.raf.cadence.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.userservice.data.dtos.RequestCreateUserDto;
import rs.raf.cadence.userservice.data.dtos.ResponseUserDto;
import rs.raf.cadence.userservice.data.entities.Permission;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.data.enums.PermissionType;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User createRequestUserDtoToUser(RequestCreateUserDto dto) {
        return new User(
                dto.getEmail(),
                dto.getUsername()
        );
    }

    public ResponseUserDto userToResponseUserDto(User user) {
        return new ResponseUserDto(
                user.getId(),
                user.getEmail(),
                user.isEmailVerified(),
                user.getUsername(),
                user.getPronouns(),
                user.getAboutMe(),
                user.getProfilePicture(),
                mapPermissions(user.getPermissions())
        );
    }

    private Set<PermissionType> mapPermissions(Set<Permission> permissions) {
        return permissions.stream()
                .map(Permission::getPermissionType)
                .collect(Collectors.toSet());
    }
}
