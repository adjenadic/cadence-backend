package rs.raf.userservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.userservice.data.dtos.CreateRequestUserDto;
import rs.raf.userservice.data.dtos.ResponseUserDto;
import rs.raf.userservice.data.entities.User;
import rs.raf.userservice.mappers.UserMapper;
import rs.raf.userservice.repositories.UserRepository;
import rs.raf.userservice.services.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Random;
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
    public ResponseUserDto createUser(CreateRequestUserDto createRequestUserDto) {
        User user = userMapper.createRequestUserDtoToUser(createRequestUserDto);
        User savedUser = userRepository.save(user);

        user.setPermissions(Set.of());
        user.setPassword(passwordEncoder.encode(Thread.currentThread().getName() + new Random().nextLong() + Thread.activeCount()));

        return userMapper.userToResponseUserDto(savedUser);
    }

    @Override
    public ResponseUserDto updateUser(ResponseUserDto user) {
        return null;
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("User not found: " + id);
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteByEmail(email);
            return true;
        } else {
            throw new RuntimeException("User not found: " + email);
        }
    }
}
