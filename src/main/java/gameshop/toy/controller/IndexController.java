package gameshop.toy.controller;

import gameshop.toy.config.auth.LoginUser;
import gameshop.toy.config.auth.dto.SessionUser;
import gameshop.toy.controller.dto.CommentResponseDto;
import gameshop.toy.controller.dto.PostResponseDto;
import gameshop.toy.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts", postsService.findAllDesc());

        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "posts-save";
    }
    
    @GetMapping("/posts/read/{id}")
    public String postsRead(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostResponseDto dto = postsService.findById(id);
        List<CommentResponseDto> commentList = dto.getCommentList();
        if (!commentList.isEmpty())
            model.addAttribute("commentList",commentList);

        model.addAttribute("post", dto);
        if (user != null) {
            model.addAttribute("userName", user.getName());

            if (dto.getUser().getId().equals(user.getId())){
                model.addAttribute("writer",true);
            }
        }

        return "post-read";
    }

    @GetMapping("/sign-up")
    public String login(Model model, @LoginUser SessionUser user){
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "sign-up";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {
        PostResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }

        return "post-update";
    }

}
