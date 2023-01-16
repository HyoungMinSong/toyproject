package gameshop.toy.controller;

import gameshop.toy.controller.dto.HelloResponseDto;
import gameshop.toy.domain.Hello;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public Hello hello() {
        Hello hello = new Hello();
        hello.setId(1L);
        hello.setName("toy");

        return hello;
    }

    @GetMapping("/hi")
    public String hi(){

        return "hi!";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloResponseDto(String name, int amount){

        return new HelloResponseDto(name, amount);
    }

    @GetMapping("/test")
    public String test(){
        return "test!";
    }
}
