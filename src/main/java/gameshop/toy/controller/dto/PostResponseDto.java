package gameshop.toy.controller.dto;

import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime modifiedTime;
    private User user;

    public PostResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getAuthor();
        this.modifiedTime = posts.getModifiedDate();
        this.user = posts.getUser();
    }
}
