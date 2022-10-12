package gameshop.toy.domain.comments;

import gameshop.toy.domain.BaseTimeEntity;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comments extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comments_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Comments(String comment, Posts posts, User user) {
        this.comment = comment;
        this.posts = posts;
        this.user = user;
    }
    public void update(String comment){
        this.comment = comment;
    }
}
