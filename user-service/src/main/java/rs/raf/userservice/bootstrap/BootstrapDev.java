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
        Permission permission1 = new Permission(PermissionType.MANAGE_USERNAMES);
        Permission permission2 = new Permission(PermissionType.MANAGE_PERMISSIONS);
        Permission permission3 = new Permission(PermissionType.DELETE_USERS);

        permission1 = permissionRepository.save(permission1);
        permission2 = permissionRepository.save(permission2);
        permission3 = permissionRepository.save(permission3);

        Set<Permission> adminPermissions = new HashSet<>();
        adminPermissions.add(permission1);
        adminPermissions.add(permission2);
        adminPermissions.add(permission3);

        User admin = new User();
        admin.setEmail("admin@cadence.com");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setPermissions(adminPermissions);
        userRepository.save(admin);

        User user = new User();
        user.setEmail("user@cadence.com");
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("user"));
        user.setPermissions(new HashSet<>());
        userRepository.save(user);
    }
}
