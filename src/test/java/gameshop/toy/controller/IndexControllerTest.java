package gameshop.toy.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class IndexControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void 메인페이지_로딩() {
        String body = restTemplate.getForObject("/", String.class);

        Assertions.assertThat(body).contains("스프링 부트 웹 서비스");
    }
}