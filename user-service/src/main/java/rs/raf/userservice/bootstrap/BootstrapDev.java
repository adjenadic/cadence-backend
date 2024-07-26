package rs.raf.userservice.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.userservice.data.entities.Permission;
import rs.raf.userservice.data.entities.User;
import rs.raf.userservice.data.enums.PermissionType;
import rs.raf.userservice.repositories.PermissionRepository;
import rs.raf.userservice.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class BootstrapDev implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        Permission permission1 = new Permission(PermissionType.PERMISSION_1);
        Permission permission2 = new Permission(PermissionType.PERMISSION_2);

        permission1 = permissionRepository.save(permission1);
        permission2 = permissionRepository.save(permission2);

        Set<Permission> permissions = new HashSet<>();
        permissions.add(permission1);
        permissions.add(permission2);

        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("username");
        user.setPassword(passwordEncoder.encode("password"));
        user.setPermissions(permissions);

        userRepository.save(user);
    }
}
