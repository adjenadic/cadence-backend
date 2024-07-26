package rs.raf.userservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.raf.userservice.data.dtos.RequestCreateUserDto;
import rs.raf.userservice.data.dtos.RequestUpdatePasswordDto;
import rs.raf.userservice.data.dtos.RequestUpdateUsernameDto;
import rs.raf.userservice.data.dtos.ResponseUserDto;

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

    boolean deleteUserById(Long id);

    boolean deleteUserByEmail(String email);
}
