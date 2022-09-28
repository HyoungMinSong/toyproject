package gameshop.toy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import gameshop.toy.controller.dto.PostsSaveRequestDto;
import gameshop.toy.controller.dto.PostsUpdateDto;
import gameshop.toy.domain.posts.Posts;
import gameshop.toy.domain.posts.PostsRepository;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
class PostsApiControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
       mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

    }



    @Test
    public void Posts_등록된다() throws Exception {
        //given
        String title = "title";
        String content = "content";

        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        //when

        mvc.perform(post("http://localhost:" + port + "/api/v1/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

//        ResponseEntity<Long> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/posts", requestDto, Long.class);


        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> postsRepositoryAll = postsRepository.findAll();
        assertThat(postsRepositoryAll.get(0).getTitle()).isEqualTo(title);
        assertThat(postsRepositoryAll.get(0).getContent()).isEqualTo(content);
//        assertThat(postsRepositoryAll.get(0).getAuthor()).isNull();
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

        mvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updateDto)))
                .andExpect(status().isOk());

//        ResponseEntity<Long> responseEntity = testRestTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);

        //then
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(updateTitle);
        assertThat(all.get(0).getContent()).isEqualTo(updateContent);


    }
}