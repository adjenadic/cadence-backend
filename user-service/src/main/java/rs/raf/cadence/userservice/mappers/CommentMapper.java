package rs.raf.cadence.userservice.mappers;

import org.springframework.stereotype.Component;
import rs.raf.cadence.userservice.data.dtos.RequestCreateCommentDto;
import rs.raf.cadence.userservice.data.dtos.ResponseCommentDto;
import rs.raf.cadence.userservice.data.entities.Comment;
import rs.raf.cadence.userservice.data.entities.User;

@Component
public class CommentMapper {
    public Comment requestCreateCommentDtoToComment(RequestCreateCommentDto dto, User user, User commenter) {
        return new Comment(
                dto.getContent(),
                user,
                commenter
        );
    }

    public ResponseCommentDto commentToResponseCommentDto(Comment comment) {
        return new ResponseCommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getTimestamp(),
                comment.getUser().getId(),
                comment.getCommenter().getId(),
                comment.getLikes(),
                comment.isEdited()
        );
    }
}
