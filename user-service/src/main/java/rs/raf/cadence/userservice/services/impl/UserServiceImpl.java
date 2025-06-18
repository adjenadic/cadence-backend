package rs.raf.cadence.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.dtos.*;
import rs.raf.cadence.userservice.data.entities.Permission;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.data.enums.PermissionType;
import rs.raf.cadence.userservice.exceptions.*;
import rs.raf.cadence.userservice.mappers.UserMapper;
import rs.raf.cadence.userservice.repositories.PermissionRepository;
import rs.raf.cadence.userservice.repositories.UserRepository;
import rs.raf.cadence.userservice.services.MessageDispatchService;
import rs.raf.cadence.userservice.services.UserService;
import rs.raf.cadence.userservice.utils.SpringSecurityUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final MessageDispatchService messageDispatchService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new EmailNotFoundException(email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public List<ResponseUserDto> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::userToResponseUserDto).collect(Collectors.toList());
    }

    @Override
    public ResponseUserDto findUserById(Long id) {
        return userRepository.findUserById(id).map(userMapper::userToResponseUserDto).orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    public ResponseUserDto findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).map(userMapper::userToResponseUserDto).orElseThrow(() -> new EmailNotFoundException(email));
    }

    @Override
    public ResponseUserDto findUserByUsername(String username) {
        return userRepository.findUserByUsername(username).map(userMapper::userToResponseUserDto).orElseThrow(() -> new AppUsernameNotFoundException(username));
    }

    @Override
    public boolean verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(InvalidVerificationCodeException::new);

        if (user.isEmailVerified()) {
            throw new IllegalStateException("Email is already verified.");
        }

        if (System.currentTimeMillis() > user.getVerificationTokenExpiry()) {
            throw new VerificationCodeExpiredException(user.getEmail());
        }

        user.setEmailVerified(true);
        user.setVerificationTokenExpiry(0L);
        userRepository.save(user);
        return true;
    }

    @Override
    public ResponseUserDto createUser(RequestCreateUserDto requestCreateUserDto) {
        if (userRepository.findUserByEmail(requestCreateUserDto.getEmail()).isPresent()) {
            throw new EmailAlreadyTakenException(requestCreateUserDto.getEmail());
        }

        if (userRepository.findUserByUsername(requestCreateUserDto.getUsername()).isPresent()) {
            throw new UsernameAlreadyTakenException(requestCreateUserDto.getUsername());
        }

        if (!requestCreateUserDto.getPassword().equals(requestCreateUserDto.getConfirmedPassword())) {
            throw new UnmatchedPasswordException();
        }

        User user = userMapper.createRequestUserDtoToUser(requestCreateUserDto);
        user.setPermissions(Set.of());
        user.setPassword(passwordEncoder.encode(requestCreateUserDto.getPassword()));
        user.setEmailVerified(false);

        User createdUser = userRepository.save(user);

        messageDispatchService.sendAccountVerificationEmail(createdUser);

        return userMapper.userToResponseUserDto(createdUser);
    }

    @Override
    public ResponseUserDto updateEmail(RequestUpdateEmailDto requestUpdateEmailDto) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(requestUpdateEmailDto.getCurrentEmail())) {
            Optional<User> user = userRepository.findUserByEmail(requestUpdateEmailDto.getCurrentEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();

                if (userRepository.findUserByEmail(requestUpdateEmailDto.getUpdatedEmail()).isPresent()) {
                    throw new EmailAlreadyTakenException(requestUpdateEmailDto.getUpdatedEmail());
                }

                updatedUser.setEmail(requestUpdateEmailDto.getUpdatedEmail());
                updatedUser.setEmailVerified(false);
                userRepository.save(updatedUser);

                messageDispatchService.sendEmailChangeEmail(updatedUser);

                return userMapper.userToResponseUserDto(updatedUser);
            } else {
                throw new EmailNotFoundException(requestUpdateEmailDto.getCurrentEmail());
            }
        } else {
            throw new AccessDeniedException("You do not have permission to update this email.");
        }
    }

    @Override
    public ResponseUserDto updateUsername(RequestUpdateUsernameDto requestUpdateUsernameDto) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(requestUpdateUsernameDto.getEmail()) || SpringSecurityUtil.hasPermission("MANAGE_USERNAMES")) {
            Optional<User> user = userRepository.findUserByEmail(requestUpdateUsernameDto.getEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();

                Optional<User> existingUserWithUsername = userRepository.findUserByUsername(requestUpdateUsernameDto.getUsername());
                if (existingUserWithUsername.isPresent() && !existingUserWithUsername.get().getEmail().equals(requestUpdateUsernameDto.getEmail())) {
                    throw new UsernameAlreadyTakenException(requestUpdateUsernameDto.getUsername());
                }

                updatedUser.setUsername(requestUpdateUsernameDto.getUsername());
                userRepository.save(updatedUser);

                return userMapper.userToResponseUserDto(updatedUser);
            } else {
                throw new EmailNotFoundException(requestUpdateUsernameDto.getEmail());
            }
        } else {
            throw new AccessDeniedException("You do not have permission to update this username.");
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

            messageDispatchService.sendPasswordChangeEmail(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new EmailNotFoundException(requestUpdatePasswordDto.getEmail());
        }
    }

    @Override
    public ResponseUserDto updatePermissions(RequestUpdatePermissionsDto requestUpdatePermissionsDto) {
        Optional<User> user = userRepository.findUserByEmail(requestUpdatePermissionsDto.getEmail());
        if (user.isPresent()) {
            User updatedUser = user.get();

            Set<Permission> permissions = new HashSet<>();
            if (requestUpdatePermissionsDto.getPermissions() != null && !requestUpdatePermissionsDto.getPermissions().isEmpty()) {
                permissions = requestUpdatePermissionsDto.getPermissions().stream().map(permissionTypeStr -> {
                    try {
                        PermissionType permissionType = PermissionType.valueOf(permissionTypeStr);
                        Optional<Permission> permission = permissionRepository.findPermissionByPermissionType(permissionType);
                        if (permission.isEmpty()) {
                            LOGGER.warn("Permission {} not found in database", permissionType);
                        }
                        return permission.orElse(null);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                }).filter(Objects::nonNull).collect(Collectors.toSet());
            }

            updatedUser.setPermissions(permissions);
            userRepository.save(updatedUser);

            return userMapper.userToResponseUserDto(updatedUser);
        } else {
            throw new EmailNotFoundException(requestUpdatePermissionsDto.getEmail());
        }
    }

    @Override
    public ResponseUserDto updatePronouns(RequestUpdatePronounsDto requestUpdatePronounsDto) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(requestUpdatePronounsDto.getEmail()) || SpringSecurityUtil.hasPermission("MANAGE_USER_DETAILS")) {
            Optional<User> user = userRepository.findUserByEmail(requestUpdatePronounsDto.getEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();

                updatedUser.setPronouns(requestUpdatePronounsDto.getPronouns());
                userRepository.save(updatedUser);

                return userMapper.userToResponseUserDto(updatedUser);
            } else {
                throw new EmailNotFoundException(requestUpdatePronounsDto.getEmail());
            }
        } else {
            throw new AccessDeniedException("You do not have permission to update these pronouns.");
        }
    }

    @Override
    public ResponseUserDto updateAboutMe(RequestUpdateAboutMeDto requestUpdateAboutMeDto) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(requestUpdateAboutMeDto.getEmail()) || SpringSecurityUtil.hasPermission("MANAGE_USER_DETAILS")) {
            Optional<User> user = userRepository.findUserByEmail(requestUpdateAboutMeDto.getEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();

                updatedUser.setAboutMe(requestUpdateAboutMeDto.getAboutMe());
                userRepository.save(updatedUser);

                return userMapper.userToResponseUserDto(updatedUser);
            } else {
                throw new EmailNotFoundException(requestUpdateAboutMeDto.getEmail());
            }
        } else {
            throw new AccessDeniedException("You do not have permission to update this about me section.");
        }
    }

    @Override
    public ResponseUserDto updateProfilePicture(RequestUpdateProfilePictureDto requestUpdateProfilePictureDto) {
        if (SpringSecurityUtil.getPrincipalEmail().equals(requestUpdateProfilePictureDto.getEmail()) || SpringSecurityUtil.hasPermission("MANAGE_USER_DETAILS")) {
            Optional<User> user = userRepository.findUserByEmail(requestUpdateProfilePictureDto.getEmail());
            if (user.isPresent()) {
                User updatedUser = user.get();

                updatedUser.setProfilePicture(requestUpdateProfilePictureDto.getProfilePicture());
                userRepository.save(updatedUser);

                return userMapper.userToResponseUserDto(updatedUser);
            } else {
                throw new EmailNotFoundException(requestUpdateProfilePictureDto.getEmail());
            }
        } else {
            throw new AccessDeniedException("You do not have permission to update this profile picture.");
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        Optional<User> user = userRepository.findUserById(id);
        if (user.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(user.get().getEmail()) || SpringSecurityUtil.hasPermission("DELETE_USERS")) {
                userRepository.deleteById(id);
                return true;
            } else {
                throw new AccessDeniedException("You do not have permission to delete this user.");
            }
        } else {
            throw new IdNotFoundException(id);
        }
    }

    @Override
    public boolean deleteUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(user.get().getEmail()) || SpringSecurityUtil.hasPermission("DELETE_USERS")) {
                userRepository.deleteByEmail(email);
                return true;
            } else {
                throw new AccessDeniedException("You do not have permission to delete this user.");
            }
        } else {
            throw new EmailNotFoundException(email);
        }
    }
}
