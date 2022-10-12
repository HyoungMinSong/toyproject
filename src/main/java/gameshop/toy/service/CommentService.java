package gameshop.toy.service;

import gameshop.toy.controller.dto.CommentRequestDto;
import gameshop.toy.controller.dto.CommentUpdateDto;
import gameshop.toy.domain.comments.Comments;
import gameshop.toy.domain.comments.CommentsRepository;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.posts.PostsRepository;
import gameshop.toy.domain.user.User;
import gameshop.toy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final UserRepository userRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;

    @Transactional
    public Long save(String email, Long id, CommentRequestDto commentRequestDto){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email=" + email));
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id=" + id));
        commentRequestDto.setUser(user);
        commentRequestDto.setPosts(posts);

        return commentsRepository.save(commentRequestDto.toEntity()).getId();

    }
    @Transactional
    public Long update(Long commentId, CommentUpdateDto commentUpdateDto) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentId = " + commentId));
        comments.update(commentUpdateDto.getComment());

        return commentId;
    }
    @Transactional
    public Long delete(Long commentId) {
        Comments comments = commentsRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. commentId = " + commentId));
        commentsRepository.delete(comments);
        return commentId;
    }
}
