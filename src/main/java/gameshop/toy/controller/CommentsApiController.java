package gameshop.toy.controller;

import gameshop.toy.config.auth.LoginUser;
import gameshop.toy.config.auth.dto.SessionUser;
import gameshop.toy.controller.dto.CommentRequestDto;
import gameshop.toy.controller.dto.CommentUpdateDto;
import gameshop.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{id}/comments")
    public Long save(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @LoginUser SessionUser sessionUser){
        return commentService.save(sessionUser.getEmail(), id, commentRequestDto);
    }

    @PutMapping("/api/posts/{id}/comments/{commentId}")
    public Long update(@PathVariable Long commentId, @RequestBody CommentUpdateDto commentUpdateDto){
        return commentService.update(commentId, commentUpdateDto);
    }

    @DeleteMapping("/api/posts/{id}/comments/{commentId}")
    public Long delete(@PathVariable Long commentId){
        return commentService.delete(commentId);
    }
}
