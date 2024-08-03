package rs.raf.cadence.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.data.dtos.RequestCreateUserDto;
import rs.raf.cadence.userservice.data.dtos.ResponseUserDto;
import rs.raf.cadence.userservice.data.entities.Permission;

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
                user.getUsername(),
                user.getPermissions().stream().map(Permission::getPermissionType).collect(Collectors.toSet())
        );
    }
}
