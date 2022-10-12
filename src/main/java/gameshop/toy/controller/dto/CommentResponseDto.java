package gameshop.toy.controller.dto;

import gameshop.toy.domain.comments.Comments;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private LocalDateTime modifiedDate;
    private String name;
//    private Posts posts;

    public CommentResponseDto(Comments comments) {
        this.id = comments.getId();
        this.comment = comments.getComment();
        this.modifiedDate = comments.getModifiedDate();
        this.name = comments.getUser().getName();
//        this.posts = comments.getPosts();
    }
}
