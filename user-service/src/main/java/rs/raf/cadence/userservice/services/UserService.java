package rs.raf.cadence.userservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.dtos.*;
import rs.raf.userservice.data.dtos.*;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<ResponseUserDto> findAllUsers();

    ResponseUserDto findUserById(Long id);

    ResponseUserDto findUserByEmail(String email);

    ResponseUserDto findUserByUsername(String username);

    ResponseUserDto createUser(RequestCreateUserDto requestCreateUserDto);

    ResponseUserDto updateUsername(RequestUpdateUsernameDto requestUpdateUsernameDto);

    ResponseUserDto updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto);

    ResponseUserDto updatePermissions(RequestUpdatePermissionsDto requestUpdatePermissionsDto);

    boolean deleteUserById(Long id);

    boolean deleteUserByEmail(String email);
}
