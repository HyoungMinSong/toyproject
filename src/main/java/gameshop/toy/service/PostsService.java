package gameshop.toy.service;

import gameshop.toy.controller.dto.PostResponseDto;
import gameshop.toy.controller.dto.PostsListResponseDto;
import gameshop.toy.controller.dto.PostsSaveRequestDto;
import gameshop.toy.controller.dto.PostsUpdateDto;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.posts.PostsRepository;
import gameshop.toy.domain.user.User;
import gameshop.toy.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 개시글이 없습니다. id=" + id));

        return new PostResponseDto(posts);
    }

    @Transactional
    public Long save(String email, PostsSaveRequestDto postsSaveRequestDto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 이메일이 없습니다. email=" + email));
        postsSaveRequestDto.setUser(user);
        return postsRepository.save(postsSaveRequestDto.toEntity()).getId();
    }
    @Transactional
    public Long update(Long id, PostsUpdateDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());

        return id;
    }

    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream().map(PostsListResponseDto::new).collect(Collectors.toList());

    }

    @Transactional
    public void delete(Long id){
        Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. ID = " + id));
        postsRepository.delete(posts);
    }
}
