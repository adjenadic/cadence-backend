package rs.raf.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.userservice.data.dtos.UserDto;
import rs.raf.userservice.data.entities.Permission;
import rs.raf.userservice.data.entities.User;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User userDtoToUser(UserDto dto) {
        return new User(
                dto.getEmail(),
                dto.getUsername(),
                dto.getPermissions().stream().map(Permission::new).collect(Collectors.toSet())
        );
    }

    public UserDto userToUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPermissions().stream().map(Permission::getPermissionType).collect(Collectors.toSet())
        );
    }
}
