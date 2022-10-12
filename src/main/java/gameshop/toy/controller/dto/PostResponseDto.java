package gameshop.toy.controller.dto;

import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime modifiedTime;
    private User user;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.content = posts.getContent();
        this.author = posts.getAuthor();
        this.modifiedTime = posts.getModifiedDate();
        this.user = posts.getUser();
        this.commentList = posts.getComments().stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
