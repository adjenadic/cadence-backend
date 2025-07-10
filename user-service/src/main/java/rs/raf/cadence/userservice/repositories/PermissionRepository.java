package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.cadence.userservice.data.entities.Permission;
import rs.raf.cadence.userservice.data.enums.PermissionType;

import java.util.Optional;

/**
 * Repository interface for the Permission entity.
 * Extends JpaRepository to provide CRUD operations for Permission entities.
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    /**
     * Retrieves an optional permission by its permission type.
     *
     * @param permissionType the type of permission to find
     * @return an Optional containing the found permission, or an empty Optional if no permission was found with the provided type
     */
    Optional<Permission> findPermissionByPermissionType(PermissionType permissionType);
}
