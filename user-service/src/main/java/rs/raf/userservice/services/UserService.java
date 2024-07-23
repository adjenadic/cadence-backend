package rs.raf.userservice.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import rs.raf.userservice.data.dtos.UserDto;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    List<UserDto> findAll();

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    UserDto findByUsername(String username);
}
