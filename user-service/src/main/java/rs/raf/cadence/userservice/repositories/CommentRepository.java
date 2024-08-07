package rs.raf.cadence.userservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.raf.cadence.userservice.data.entities.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByUserId(Long userId);

    Optional<Comment> findCommentById(Long id);
}