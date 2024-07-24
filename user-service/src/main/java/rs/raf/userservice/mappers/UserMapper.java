package rs.raf.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.userservice.data.dtos.CreateRequestUserDto;
import rs.raf.userservice.data.dtos.ResponseUserDto;
import rs.raf.userservice.data.entities.Permission;
import rs.raf.userservice.data.entities.User;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public User createRequestUserDtoToUser(CreateRequestUserDto dto) {
        return new User(
                dto.getEmail(),
                dto.getUsername()
        );
    }

    public ResponseUserDto userToResponseUserDto(User user) {
        return new ResponseUserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPermissions().stream().map(Permission::getPermissionType).collect(Collectors.toSet())
        );
    }
}
