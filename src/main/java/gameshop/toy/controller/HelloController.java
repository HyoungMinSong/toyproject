package gameshop.toy.controller;

import gameshop.toy.domain.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public Hello hello() {
        Hello hello = new Hello();
        hello.setId(1L);
        hello.setName("toy");

        return hello;
    }
}
