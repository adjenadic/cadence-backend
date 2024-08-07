package rs.raf.cadence.userservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import rs.raf.cadence.userservice.data.dtos.RequestCreateCommentDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateCommentDto;
import rs.raf.cadence.userservice.data.dtos.ResponseCommentDto;
import rs.raf.cadence.userservice.data.entities.Comment;
import rs.raf.cadence.userservice.data.entities.User;
import rs.raf.cadence.userservice.exceptions.CommentNotFoundException;
import rs.raf.cadence.userservice.exceptions.IdNotFoundException;
import rs.raf.cadence.userservice.mappers.CommentMapper;
import rs.raf.cadence.userservice.repositories.CommentRepository;
import rs.raf.cadence.userservice.repositories.UserRepository;
import rs.raf.cadence.userservice.services.CommentService;
import rs.raf.cadence.userservice.utils.SpringSecurityUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public List<ResponseCommentDto> findCommentsByUserId(Long userId) {
        return commentRepository.findCommentByUserId(userId).stream()
                .map(commentMapper::commentToResponseCommentDto)
                .collect(Collectors.toList());
    }

    public ResponseCommentDto findCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        return commentMapper.commentToResponseCommentDto(comment);
    }

    public ResponseCommentDto createComment(RequestCreateCommentDto requestCreateCommentDto) {
        User user = userRepository.findById(requestCreateCommentDto.getUserId()).orElseThrow(() -> new IdNotFoundException(requestCreateCommentDto.getUserId()));
        User commenter = userRepository.findById(requestCreateCommentDto.getCommenterId()).orElseThrow(() -> new IdNotFoundException(requestCreateCommentDto.getCommenterId()));
        Comment comment = commentMapper.requestCreateCommentDtoToComment(requestCreateCommentDto, user, commenter);
        commentRepository.save(comment);
        return commentMapper.commentToResponseCommentDto(comment);
    }

    public ResponseCommentDto updateComment(RequestUpdateCommentDto requestUpdateCommentDto) {
        Optional<Comment> comment = commentRepository.findCommentById(requestUpdateCommentDto.getCommentId());
        if (comment.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(comment.get().getCommenter().getEmail())) {
                Comment updatedComment = comment.get();

                updatedComment.setContent(requestUpdateCommentDto.getContent());
                updatedComment.setTimestamp(System.currentTimeMillis());

                commentRepository.save(updatedComment);
                return commentMapper.commentToResponseCommentDto(updatedComment);
            } else {
                throw new AccessDeniedException("You do not have permission to update this profile picture.");
            }
        } else {
            throw new CommentNotFoundException(requestUpdateCommentDto.getCommentId());
        }
    }

    public ResponseCommentDto likeComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
        return commentMapper.commentToResponseCommentDto(comment);
    }

    public ResponseCommentDto unlikeComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        comment.setLikes(comment.getLikes() - 1);
        commentRepository.save(comment);
        return commentMapper.commentToResponseCommentDto(comment);
    }

    public boolean deleteCommentById(Long id) {
        Optional<Comment> comment = commentRepository.findCommentById(id);
        if (comment.isPresent()) {
            if (SpringSecurityUtil.getPrincipalEmail().equals(comment.get().getCommenter().getEmail()) ||
                    SpringSecurityUtil.hasPermission("DELETE_COMMENTS")) {
                commentRepository.deleteById(id);
                return true;
            } else {
                throw new AccessDeniedException("You do not have permission to delete this comment.");
            }
        } else {
            throw new IdNotFoundException(id);
        }
    }
}
