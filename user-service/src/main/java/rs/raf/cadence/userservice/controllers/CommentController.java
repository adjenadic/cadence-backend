package rs.raf.cadence.userservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import rs.raf.cadence.userservice.data.dtos.RequestCreateCommentDto;
import rs.raf.cadence.userservice.data.dtos.RequestUpdateCommentDto;
import rs.raf.cadence.userservice.data.dtos.ResponseCommentDto;
import rs.raf.cadence.userservice.services.CommentService;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping(value = "/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping(value = "/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findCommentById(id));
    }

    @GetMapping(value = "/user-id/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findCommentsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(commentService.findCommentsByUserId(userId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createComment(@RequestBody RequestCreateCommentDto requestCreateCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.createComment(requestCreateCommentDto);
        return ResponseEntity.ok(responseCommentDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateComment(@RequestBody RequestUpdateCommentDto requestUpdateCommentDto) {
        ResponseCommentDto responseCommentDto = commentService.updateComment(requestUpdateCommentDto);
        return ResponseEntity.ok(responseCommentDto);
    }

    @PutMapping(value = "/like/{id}")
    public ResponseEntity<?> likeComment(@PathVariable Long id) {
        ResponseCommentDto responseCommentDto = commentService.likeComment(id);
        return ResponseEntity.ok(responseCommentDto);
    }

    @PutMapping(value = "/unlike/{id}")
    public ResponseEntity<?> unlikeComment(@PathVariable Long id) {
        ResponseCommentDto responseCommentDto = commentService.unlikeComment(id);
        return ResponseEntity.ok(responseCommentDto);
    }

    @DeleteMapping(value = "/delete/id/{id}", produces = MediaType.ALL_VALUE)
    @Transactional
    public ResponseEntity<?> deleteCommentById(@PathVariable Long id) {
        boolean isDeleted = commentService.deleteCommentById(id);
        return ResponseEntity.ok(isDeleted);
    }
}
