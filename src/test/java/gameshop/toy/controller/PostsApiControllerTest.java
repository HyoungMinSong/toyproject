package gameshop.toy.controller;

import gameshop.toy.controller.dto.PostsSaveRequestDto;
import gameshop.toy.controller.dto.PostsUpdateDto;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.posts.PostsRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void Posts_등록된다() {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        //when

        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/posts", requestDto, Long.class);


        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsRepositoryAll = postsRepository.findAll();
        assertThat(postsRepositoryAll.get(0).getTitle()).isEqualTo(title);
        assertThat(postsRepositoryAll.get(0).getContent()).isEqualTo(content);
        assertThat(postsRepositoryAll.get(0).getAuthor()).isNull();
    }

    @Test  //현재 이 테스트는 오류가 난다.
    public void Posts_수정된다() throws Exception {
        //given
        Posts post = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        Long id = post.getId();
        String updateTitle = "title2";
        String updateContent = "content2";

        PostsUpdateDto updateDto = PostsUpdateDto.builder()
                .title(updateTitle)
                .content(updateContent).build();

        String url = "http://localhost:" + port + "/api/v1/posts/" +id;

        HttpEntity<PostsUpdateDto> requestEntity = new HttpEntity<>(updateDto);

        //when

        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);


    }
}