package rs.raf.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.userservice.data.entities.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
