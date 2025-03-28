package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.userservice.data.entities.Permission;
import rs.raf.cadence.userservice.data.enums.PermissionType;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findPermissionByPermissionType(PermissionType permissionType);
}
