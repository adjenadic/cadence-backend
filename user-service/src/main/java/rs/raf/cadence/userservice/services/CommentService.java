package rs.raf.cadence.userservice.services;

import rs.raf.cadence.userservice.data.dtos.RequestCreateCommentDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateCommentDto;
import rs.raf.cadence.userservice.data.dtos.ResponseCommentDto;

import java.util.List;

public interface CommentService {
    List<ResponseCommentDto> findCommentsByUserId(Long id);

    ResponseCommentDto findCommentById(Long id);

    ResponseCommentDto createComment(RequestCreateCommentDto dto);

    ResponseCommentDto updateComment(RequestUpdateCommentDto dto);

    ResponseCommentDto likeComment(Long id);

    ResponseCommentDto unlikeComment(Long id);

    boolean deleteCommentById(Long id);
}
