package rs.raf.userservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.userservice.data.dtos.RequestCreateUserDto;
import rs.raf.userservice.data.dtos.RequestUpdatePasswordDto;
import rs.raf.userservice.data.dtos.RequestUpdateUsernameDto;
import rs.raf.userservice.data.dtos.ResponseUserDto;
import rs.raf.userservice.data.entities.User;
import rs.raf.userservice.mappers.UserMapper;
import rs.raf.userservice.repositories.UserRepository;
import rs.raf.userservice.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RabbitTemplate rabbitTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getPermissions().stream()
                        .map(permission -> new org.springframework.security.core.authority.SimpleGrantedAuthority(permission.getPermissionType().name()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public List<ResponseUserDto> findAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::userToResponseUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseUserDto findUserById(Long id) {
        return userRepository.findUserById(id)
                .map(userMapper::userToResponseUserDto)
                .orElse(null);
    }

    @Override
    public ResponseUserDto findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userMapper::userToResponseUserDto)
                .orElse(null);
    }

    @Override
    public ResponseUserDto findUserByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(userMapper::userToResponseUserDto)
                .orElse(null);
    }

    @Override
    public ResponseUserDto createUser(RequestCreateUserDto requestCreateUserDto) {
        User user = userMapper.createRequestUserDtoToUser(requestCreateUserDto);

        user.setPermissions(Set.of());
        user.setPassword(passwordEncoder.encode(requestCreateUserDto.getPassword()));

        User savedUser = userRepository.save(user);

        return userMapper.userToResponseUserDto(savedUser);
    }

    @Override
    public ResponseUserDto updateUsername(RequestUpdateUsernameDto requestUpdateUsernameDto) {
        Optional<User> user = userRepository.findUserByEmail(requestUpdateUsernameDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setUsername(requestUpdateUsernameDto.getUsername());
            userRepository.save(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + requestUpdateUsernameDto.getEmail());
        }
    }

    @Override
    public ResponseUserDto updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto) {
        Optional<User> user = userRepository.findUserByEmail(requestUpdatePasswordDto.getPassword());
        if (user.isPresent()) {
            User updatedUser = user.get();
            updatedUser.setPassword(passwordEncoder.encode(requestUpdatePasswordDto.getPassword()));
            userRepository.save(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + requestUpdatePasswordDto.getEmail());
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteByEmail(email);
            return true;
        } else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}
