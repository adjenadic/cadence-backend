package rs.raf.userservice.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.userservice.data.dtos.*;
import rs.raf.userservice.data.entities.Permission;
import rs.raf.userservice.data.entities.User;
import rs.raf.userservice.data.enums.PermissionType;
import rs.raf.userservice.mappers.UserMapper;
import rs.raf.userservice.repositories.PermissionRepository;
import rs.raf.userservice.repositories.UserRepository;
import rs.raf.userservice.services.UserService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;
    private final RabbitTemplate rabbitTemplate;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PermissionRepository permissionRepository, UserMapper userMapper, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.permissionRepository = permissionRepository;
        this.userMapper = userMapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found."));
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                new ArrayList<>()
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
            throw new UsernameNotFoundException(requestUpdateUsernameDto.getEmail());
        }
    }

    @Override
    public ResponseUserDto updatePassword(RequestUpdatePasswordDto requestUpdatePasswordDto) {
        Optional<User> user = userRepository.findUserByEmail(requestUpdatePasswordDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get();

            if (!passwordEncoder.matches(requestUpdatePasswordDto.getCurrentPassword(), updatedUser.getPassword())) {
                throw new IllegalArgumentException("The inputted current password is incorrect.");
            }

            if (!requestUpdatePasswordDto.getUpdatedPassword().equals(requestUpdatePasswordDto.getUpdatedPasswordConfirmation())) {
                throw new IllegalArgumentException("The inputted passwords do not match.");
            }

            updatedUser.setPassword(passwordEncoder.encode(requestUpdatePasswordDto.getUpdatedPassword()));
            userRepository.save(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new UsernameNotFoundException("User with email " + requestUpdatePasswordDto.getEmail() + " not found.");
        }
    }

    @Override
    public ResponseUserDto updatePermissions(RequestUpdatePermissionsDto requestUpdatePermissionsDto) {
        Optional<User> user = userRepository.findUserByEmail(requestUpdatePermissionsDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get();

            Set<Permission> permissions = new HashSet<>();
            if (requestUpdatePermissionsDto.getPermissions() != null && !requestUpdatePermissionsDto.getPermissions().isEmpty()) {
                permissions = requestUpdatePermissionsDto.getPermissions().stream()
                        .map(permissionTypeStr -> {
                            try {
                                PermissionType permissionType = PermissionType.valueOf(permissionTypeStr);
                                return permissionRepository.findPermissionByPermissionType(permissionType)
                                        .orElse(null);
                            } catch (IllegalArgumentException e) {
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet());
            }

            updatedUser.setPermissions(permissions);
            userRepository.save(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + requestUpdatePermissionsDto.getEmail());
        }
    }


    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("User with id " + id + " not found.");
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            userRepository.deleteByEmail(email);
            return true;
        } else {
            throw new UsernameNotFoundException("User with email " + email + " not found.");
        }
    }
}
