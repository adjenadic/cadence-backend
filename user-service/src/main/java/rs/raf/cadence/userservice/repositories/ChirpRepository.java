package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.cadence.userservice.data.entities.Chirp;

import java.util.List;

public interface ChirpRepository extends JpaRepository<Chirp, Long> {
    List<Chirp> findChirpsByUserIdOrderByTimestampDesc(Long userId);
}
