package gameshop.toy.controller.dto;

import gameshop.toy.domain.comments.Comments;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.user.User;
import lombok.Builder;


public class CommentRequestDto {
//    private Long id;
    private String comment;
    private User user;
    private Posts posts;

    @Builder
    public CommentRequestDto(String comment) {
        this.comment = comment;
    }

    public Comments toEntity(){
        return Comments.builder()
                .comment(comment)
                .user(user)
                .posts(posts)
                .build();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }
}
