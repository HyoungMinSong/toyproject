package gameshop.toy.controller;

import gameshop.toy.config.auth.LoginUser;
import gameshop.toy.config.auth.dto.SessionUser;
import gameshop.toy.controller.dto.CommentRequestDto;
import gameshop.toy.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentsApiController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{id}/comments")
    public Long save(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @LoginUser SessionUser sessionUser){
        return commentService.save(sessionUser.getEmail(), id, commentRequestDto);
    }
}
